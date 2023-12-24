package com.test.splitwise.services;

import com.test.splitwise.models.Group;
import com.test.splitwise.models.User;
import com.test.splitwise.repositories.GroupRepository;
import com.test.splitwise.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public Group createGroup(String groupName, String adminName) {
        Group group = new Group();

        // Set the name of the Group
        Optional<Group> groupOptional = groupRepository.findByName(groupName);
        if(groupOptional.isPresent()) {
            throw new RuntimeException("Group name already exists!");
        }
        group.setName(groupName);

        // Set the admin for the Group
        Optional<User> userOptional = userRepository.findByName(adminName);
        if(userOptional.isEmpty()) {
            throw new RuntimeException("No User found with the given Admin name!");
        }
        group.setAdmin(userOptional.get());

        // Save the Group in the DB
        group = groupRepository.save(group);
        return group;
    }

    @Transactional
    public Group addGroupMember(String groupName, String userName) {
        // Get the User
        Optional<User> userOptional = userRepository.findByName(userName);
        if(userOptional.isEmpty()) {
            throw new RuntimeException("No User exists with the entered User name!");
        }
        User user = userOptional.get();

        // Get the Group
        Optional<Group> groupOptional = groupRepository.findByName(groupName);
        if(groupOptional.isEmpty()) {
            throw new RuntimeException("No Group exists with the entered Group name!");
        }
        Group group = groupOptional.get();

        // @Transactional is used so that the members data is also retrieved using the same transaction session
        // Check if User already part of the Group
        List<User> groupMembers = group.getMembers();
        if(groupMembers.contains(user)) {
            throw new RuntimeException("User is already a part of this group!");
        }

        // Add the User to the Group
        groupMembers.add(user);
        group.setMembers(groupMembers);

        // Update the Group in the DB
        group = groupRepository.save(group);
        return group;
    }

}
