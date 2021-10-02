package com.sparta.springcore.service;

import com.sparta.springcore.model.Folder;
import com.sparta.springcore.model.User;
import com.sparta.springcore.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;

    public List<Folder> addFolders(List<String> folderNames, User user) {
        List<Folder> folderList = new ArrayList<>();


        for (String folderName : folderNames) {
            Folder folder = new Folder(folderName, user);
            folderList.add(folder);
        }

        folderList = folderRepository.saveAll(folderList);

        return folderList;
    }

    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }
}
