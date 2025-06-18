package ru.netology.repository;

import ru.netology.model.Post;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Stub
public class PostRepository {
    protected ConcurrentHashMap<Long, Post> postsMap = new ConcurrentHashMap<>();
    private long count = 0;
    HttpServletResponse resp = null;

    public List<Post> all() {
        for (Long key : postsMap.keySet()) {
            new ArrayList<>().add(postsMap.get(key));
        }

        return new ArrayList<>();
    }

    public Optional<Post> getById(long id) {
        try {
            if (!postsMap.containsKey(id)) ;
        } catch (Exception e) {
            e.printStackTrace();

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return Optional.of(postsMap.get(id));
    }

    public Post save(Post post) {

        if (new Post().getId() == 0) {
            postsMap.put(count, post);
            count++;
        } else if (postsMap.containsKey(new Post().getId())) {
            postsMap.replace(new Post().getId(), post);
        } else {
            postsMap.put(new Post().getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        postsMap.remove(id);
    }
}
