package dev.vorstu.controller;

import dev.vorstu.dto.group.GroupRequestDto;
import dev.vorstu.dto.group.GroupResponseDto;
import dev.vorstu.entity.User;
import dev.vorstu.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public GroupResponseDto createGroup(@Valid @RequestBody GroupRequestDto dto){
        return groupService.createGroup(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public Page<GroupResponseDto> getAllGroups(Pageable pageable){
        return groupService.getAllGroups(pageable);
    }

    @GetMapping("/{id}")
    public GroupResponseDto getGroup(@PathVariable Long id, @AuthenticationPrincipal User user){
        return groupService.getGroup(id, user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public GroupResponseDto changeGroup(@Valid @RequestBody GroupRequestDto dto, @PathVariable Long id){
        return groupService.changeGroup(dto, id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public GroupResponseDto deleteGroup(@PathVariable Long id){
        return groupService.deleteGroup(id);
    }

}
