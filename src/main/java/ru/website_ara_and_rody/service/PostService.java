package ru.website_ara_and_rody.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.website_ara_and_rody.dto.CreatePostDto;
import ru.website_ara_and_rody.dto.PostDto;
import ru.website_ara_and_rody.entity.Post;
import ru.website_ara_and_rody.entity.Users;
import ru.website_ara_and_rody.mapper.PostMapper;
import ru.website_ara_and_rody.repositoty.PostRepository;
import ru.website_ara_and_rody.repositoty.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    @Transactional
    public Post createPost(Long id , CreatePostDto createPostDto , List<MultipartFile> files){
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким id " + id + " не найден!"));
        Post post = postMapper.toEntity(createPostDto);
        post.setUsers(users);
        users.getPosts().add(post);
       return postRepository.save(post);
    }

    public List<PostDto> getALLPost(){
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateTitlePost(Long id , String newTitle){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Поста с таким id " + id + " не найден"));

        post.setTitle(newTitle);
        postRepository.save(post);
    }
    public void updateTextPost(Long id , String newText){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Поста с таким id " + id + " не найден"));

        post.setText(newText);
        postRepository.save(post);
    }

    public void deletePost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Поста с таким id " + id + " не найден"));
        postRepository.delete(post);
    }
}
