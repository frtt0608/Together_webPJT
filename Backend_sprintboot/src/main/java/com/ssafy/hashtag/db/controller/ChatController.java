package com.ssafy.hashtag.db.controller;

import java.util.List;

import com.ssafy.hashtag.db.dto.ChatDto;
import com.ssafy.hashtag.db.dto.ChatroomDto;
import com.ssafy.hashtag.db.service.ChatService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;


@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Controller
@RequestMapping("/api/chat")
@Api(value = "SSAFY", description = "Chat Resouces Management 2019")
public class ChatController {
  public static final Logger logger = LoggerFactory.getLogger(ChatController.class);

  @Autowired
  private ChatService chatservice;

  // 채팅방 create
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ResponseEntity<ChatDto> Create(@RequestBody ChatDto chatdto) throws Exception {
    ChatDto chat = chatservice.Create(chatdto);
    
    return new ResponseEntity<ChatDto>(chat, HttpStatus.OK);
  }
  
  // opener가 채팅방 삭제
  @RequestMapping(value = "/{chat_pk}/delete/{user_pk}", method = RequestMethod.GET)
  public ResponseEntity<String> Delete(@PathVariable int chat_pk, @PathVariable int user_pk) throws Exception {
    ChatDto chat = new ChatDto();
    chat.setOpener_id(user_pk);
    chat.setChat_pk(chat_pk);
    chatservice.Delete(chat);
    return new ResponseEntity<String> ("채팅방을 삭제했습니다.", HttpStatus.OK);
  }

  // location에 따라 채팅방 조회
  @RequestMapping(value="/locate/{location}", method = RequestMethod.GET)
  public ResponseEntity<List<ChatDto>> Locate_chat(@PathVariable String location) throws Exception {
    List<ChatDto> chatrooms = chatservice.Locate_chat(location);
    return new ResponseEntity<List<ChatDto>>(chatrooms, HttpStatus.OK);
  }

  // 채팅방에 user가 입장
  @RequestMapping(value = "/{chat_pk}/enter/{user_pk}", method = RequestMethod.POST)
  public ResponseEntity<Integer> Enter(@RequestBody ChatroomDto chatroomdto)throws Exception {
    int cnt = chatservice.Enter(chatroomdto);

    return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
  }

  // 채팅방에 user가 퇴장
  @RequestMapping(value = "/{chat_pk}/leave/{user_pk}", method = RequestMethod.GET)
  public ResponseEntity<Boolean> Leave(@PathVariable int chat_pk, @PathVariable int user_pk) throws Exception {
    ChatroomDto chatroomdto = new ChatroomDto();
    chatroomdto.setChat_id(chat_pk);
    chatroomdto.setUser_id(user_pk);
    Boolean flag = chatservice.Leave(chatroomdto);
    return new ResponseEntity<Boolean> (flag, HttpStatus.OK);
  }
}