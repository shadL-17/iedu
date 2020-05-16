package cn.shadl.ieduservicecommunity.service;

import cn.shadl.ieducommonbeans.domain.Post;
import cn.shadl.ieduservicecommunity.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findByPid(Integer pid) {
        List<Post> posts = postRepository.findByPid(pid);
        return (posts!=null && !posts.isEmpty()) ? posts.get(0) : null;
    }

    public List<Post> findTopThemes() {
        return postRepository.findTopThemes();
    }

    public List<Post> findNoneTopThemes() {
        return postRepository.findNoneTopThemes();
    }

    public List<Post> findByParentOrderByFloorAsc(Integer parent) {
        return postRepository.findByParentOrderByFloorAsc(parent);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Post saveNewThemePost(Post post) {
        Post returnPost = postRepository.save(post);
        returnPost.setParent(returnPost.getPid());
        return postRepository.save(returnPost);
    }

    public List<Post> findByCreator(Integer uid) {
        return postRepository.findByCreator(uid);
    }

    public Integer findAmountsOfReplies(Integer pid) {
        return postRepository.findAmountsOfReplies(pid);
    }
}
