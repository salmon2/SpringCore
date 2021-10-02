package com.sparta.springcore.controller;

import com.sparta.springcore.model.Folder;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.model.User;
import com.sparta.springcore.model.dto.FolderRequestDto;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/folders")
    public List<Folder> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<Folder> folders = folderService.getFolders(userDetails.getUser());
        return folders;
    }

    @GetMapping("/api/folders/{folderId}/products")
    public Page<Product> getProductsInFolder(@PathVariable Long folderId,
                                             @RequestParam int page,
                                             @RequestParam int size,
                                             @RequestParam String sortBy,
                                             @RequestParam  boolean isAsc,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        page = page-1;
        return folderService.getProductsInFolder(folderId, page, size, sortBy, isAsc, userDetails.getUser());
    }



}

