package ru.website_ara_and_rody.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.website_ara_and_rody.dto.PostDto;
import ru.website_ara_and_rody.entity.Post;
import ru.website_ara_and_rody.entity.Users;
import ru.website_ara_and_rody.mapper.PostMapper;
import ru.website_ara_and_rody.repositoty.PostRepository;
import ru.website_ara_and_rody.repositoty.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public Post createPost( Long id ,PostDto postDto){
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким id " + id + " не найден!"));
        Post post = postMapper.toEntity(postDto);
        post.setUsers(users);
        users.getPosts().add(post);
       return postRepository.save(post);
    }
}
