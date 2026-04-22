package ru.website_ara_and_rody.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.website_ara_and_rody.dto.CreatePostDto;
import ru.website_ara_and_rody.dto.PostDto;
import ru.website_ara_and_rody.entity.Image;
import ru.website_ara_and_rody.entity.Post;
import ru.website_ara_and_rody.entity.Users;
import ru.website_ara_and_rody.mapper.PostMapper;
import ru.website_ara_and_rody.repositoty.PostRepository;
import ru.website_ara_and_rody.repositoty.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Transactional
    public Post createPost(Long id , CreatePostDto createPostDto , List<MultipartFile> files) throws IOException {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким id " + id + " не найден!"));
        Post post = postMapper.toEntity(createPostDto);
        List<Image> images = post.getImages();
        if(files != null && !files.isEmpty()){
            for (MultipartFile file : files){
                Image image = imageService.toImageEntity(file);
                image.setPost_image(post);
                images.add(image);
            }
        }
        post.setUsers(users);
        users.getPosts().add(post);
       return postRepository.save(post);
    }

    public List<PostDto> getALLPost(){
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTitlePost(Long id , String newTitle){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Поста с таким id " + id + " не найден"));

        post.setTitle(newTitle);
        postRepository.save(post);
    }

    @Transactional
    public void updateTextPost(Long id , String newText){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Поста с таким id " + id + " не найден"));

        post.setText(newText);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Поста с таким id " + id + " не найден"));
        postRepository.delete(post);
    }
}
