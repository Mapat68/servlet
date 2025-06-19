package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    protected ConcurrentHashMap<Long, Post> postsMap = new ConcurrentHashMap<>();
    private AtomicLong count = new AtomicLong();

    public List<Post> all() {
        List<Post> postsList = new ArrayList<>();
        for (Long key : postsMap.keySet()) {
            postsList.add(postsMap.get(key));
        }

        return postsList;
    }

    public Optional<Post> getById(long id) {

        return Optional.of(postsMap.get(id));
    }

    public Post save(Post post) {

        if (new Post().getId() == 0) {
            postsMap.put(count.longValue(), post);
            count.incrementAndGet();
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
