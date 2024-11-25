package com.project.grpcproject.server;

import com.project.grpcproject.service.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new UserService())
                .build();

        System.out.println("Server started on port 50051");
        server.start();
        server.awaitTermination();
    }
}
