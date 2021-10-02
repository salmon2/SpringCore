package com.sparta.springcore.controller;

import com.sparta.springcore.model.Folder;
import com.sparta.springcore.model.User;
import com.sparta.springcore.model.dto.FolderRequestDto;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @PostMapping("/api/folders")
    public List<Folder> addFolder(@RequestBody FolderRequestDto folderRequestDto,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<String> folderNames = folderRequestDto.getFolderNames();
        User user = userDetails.getUser();
        List<Folder> folders = folderService.addFolders(folderNames, user);

        return folders;
    }



}

