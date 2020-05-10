package cn.shadl.ieduservicecommunity.controller;

import cn.shadl.ieducommonbeans.domain.Post;
import cn.shadl.ieducommonbeans.domain.dto.PostDTO;
import cn.shadl.ieduservicecommunity.service.PostService;
import cn.shadl.ieduservicecommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping("/findByPid")
    public Post findByPid(Integer pid) {
        return postService.findByPid(pid);
    }

    @RequestMapping("/findTopThemes")
    public List<Post> findTopThemes() {
        return postService.findTopThemes();
    }

    @RequestMapping("/findNoneTopThemes")
    public List<Post> findNoneTopThemes() {
        return postService.findNoneTopThemes();
    }

    @GetMapping("/readThemePost")
    public List<PostDTO> findByParentOrderByFloorAsc(Integer pid) {
        List<Post> posts = postService.findByParentOrderByFloorAsc(pid);
        List<PostDTO> dtos = new ArrayList<>();
        for (Post post : posts) {
            PostDTO dto = new PostDTO();
            dto.setPid(post.getPid());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setPublishDate(post.getPublishDate());
            dto.setLastUpdateDate(post.getLastUpdateDate());
            dto.setCreator(userService.findByUid(post.getCreator()));
            dto.setType(post.getType());
            dto.setStatus(post.getStatus());
            dto.setParent(post.getParent());
            dto.setFloor(post.getFloor());
            dtos.add(dto);
        }
        return dtos;
    }

    @PostMapping("/publish")
    public Post publishNewPost(String title, String content, String type, Integer uid) {
        long timeMillis = System.currentTimeMillis();
        Post post = new Post();
        post.setPid(null);
        post.setTitle(title);
        post.setContent(content);
        post.setType(type);
        post.setCreator(uid);
        post.setFloor(1);
        post.setParent(null);
        post.setStatus("normal");
        post.setPublishDate(new Date(timeMillis));
        post.setLastUpdateDate(new Date(timeMillis));
        return postService.saveNewThemePost(post);
    }

}
