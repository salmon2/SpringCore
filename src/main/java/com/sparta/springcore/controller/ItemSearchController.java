package com.sparta.springcore.controller;
import com.sparta.springcore.model.dto.SignupRequestDto;
import com.sparta.springcore.service.ItemSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemSearchController {
    private final ItemSearchService itemSearchService;

    @GetMapping("/api/search")
    @ResponseBody
    public List<SignupRequestDto.ItemDto> getItems(@RequestParam String query) throws IOException {
        List<SignupRequestDto.ItemDto> itemDtoList = itemSearchService.getItems(query);

        return itemDtoList;
    }
}
