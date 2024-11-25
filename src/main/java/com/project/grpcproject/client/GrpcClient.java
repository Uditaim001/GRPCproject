package com.project.grpcproject.client;

import com.example.grpc.UserProto;
import com.example.grpc.UserServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args){
        ManagedChannel channel= ManagedChannelBuilder.forAddress("localhost",50051)
                .usePlaintext().build();
        UserServiceGrpc.UserServiceBlockingStub stub= UserServiceGrpc.newBlockingStub(channel);
        UserProto.User user = UserProto.User.newBuilder()
                .setId(2)
                .setName("James May")
                .setEmail("JamesMaysonthegrandtour@gmail.com")
                .setDateOfBirth("1996-09-12")
                .build();
        UserProto.SuccessMessage response = stub.createUser(user);
        System.out.println(response.getMessage());

        UserProto.GetUserRequest getUserRequest = UserProto.GetUserRequest.newBuilder().setId(2).build();
        UserProto.User receivedUser = stub.getUser(getUserRequest);
        System.out.println("User Found: \n" + receivedUser);

        UserProto.UserList userList = stub.listUsers(Empty.getDefaultInstance());
        System.out.println("User List: " + userList);

        channel.shutdown();
    }
}