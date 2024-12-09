package com.project.grpcproject.client;

import com.example.grpc.UserProto;
import com.example.grpc.UserServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
public class GrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClient.class);
    public static void main(String[] args){
            Properties properties = new Properties();
            try {
                properties.load(GrpcClient.class.getClassLoader().getResourceAsStream("application.properties"));
                String host = properties.getProperty("grpc.server.host");
                int port = Integer.parseInt(properties.getProperty("grpc.server.port"));
                ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                        .usePlaintext()
                        .build();
                logger.info("Connected to gRPC server at {}:{}", host, port);
                UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
                try {
                    UserProto.User user = UserProto.User.newBuilder()
                            .setId(1)
                            .setName("Jimmy Neesham")
                            .setEmail("JimmyNeeshamthegrandtour@gmail.com")
                            .setDateOfBirth("1993-11-16")
                            .build();
                    UserProto.SuccessMessage response = stub.createUser(user);
                    logger.info("User creation successful: {}", response.getMessage());
                } catch (Exception e) {
                    logger.error("Error during user creation: ", e);
                }
                try {
                    UserProto.GetUserRequest getUserRequest = UserProto.GetUserRequest.newBuilder().setId(1).build();
                    UserProto.User receivedUser = stub.getUser(getUserRequest);
                    logger.info("User found: \n{}", receivedUser);
                } catch (Exception e) {
                    logger.error("Error retrieving user: ", e);
                }
                try {
                    UserProto.UserList userList = stub.listUsers(Empty.getDefaultInstance());
                    logger.info("User List: {}", userList);
                } catch (Exception e) {
                    logger.error("Error fetching user list: ", e);
                }
                channel.shutdown();
                logger.info("gRPC channel shut down successfully");

            } catch (IOException e) {
                logger.error("Error loading configuration: ", e);
            } catch (Exception e) {
                logger.error("Unexpected error occurred: ", e);
            }
        }
}
