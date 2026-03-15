package com.task.Security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.task.ENUM.Permission;
import com.task.ENUM.Role;

@Component
public class RolePermissionConfig {

    // Giving Permissions as per Role
    public Map<Role, Set<Permission>> getRoleBasedPermissions() { // // class level , Map having key and value pair features , hence here as per role will give permission

        Map<Role, Set<Permission>> map = new HashMap<>();

        map.put(Role.ADMIN, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.ISSUE_CREATE,
                Permission.ISSUE_EDIT,
                Permission.ISSUE_DELETE,
                Permission.COMMENT_ADD,
                Permission.COMMENT_DELETE,
                Permission.USER_MANAGE
        )));

        map.put(Role.MANAGER, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.ISSUE_EDIT,
                Permission.ISSUE_CREATE,
                Permission.COMMENT_ADD
        )));

        map.put(Role.DEVELOPER, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.ISSUE_EDIT,
                Permission.COMMENT_ADD
        )));

        map.put(Role.TESTER, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.COMMENT_ADD
        )));

        return map;
    }
}