package com.project.grpcproject.server;

import com.project.grpcproject.client.GrpcClient;
import com.project.grpcproject.interceptor.GrpcInterceptor;
import com.project.grpcproject.service.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcServer {
    private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class);

    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new UserService())
                .intercept(new GrpcInterceptor())
                .build();

        logger.info("Server started on port 50051");
        server.start();
        server.awaitTermination();
    }
}
