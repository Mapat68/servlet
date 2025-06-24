package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String get = "GET";
    private static final String post = "POST";
    private static final String delete = "DELETE";
    private static final String api = "/api/posts";


    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("ru.netology");

        final var repository = context.getBean(PostRepository.class);
        final var service = context.getBean(PostService.class);
        controller = (PostController) context.getBean("postController");
        final var isSame = service == context.getBean("postService");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {


        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            // primitive routing
            if (method.equals(get) && path.equals(api)) {
                controller.all(resp);
                return;
            }
            if (method.equals(get) && path.matches(api + "\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.getById(id, resp);
                return;
            }
            if (method.equals(post) && path.equals(api)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(delete) && path.matches(api + "\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

