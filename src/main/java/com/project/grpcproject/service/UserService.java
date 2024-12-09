package com.project.grpcproject.service;

import com.example.grpc.UserProto;
import com.example.grpc.UserServiceGrpc;
import com.project.grpcproject.exception.Exception;
import com.project.grpcproject.validator.UserValidator;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final ConcurrentHashMap<Integer, UserProto.User> userStore = new ConcurrentHashMap<>();
    @Autowired
    private UserValidator userValidator;
    @Override
    public void createUser(UserProto.User request, StreamObserver<UserProto.SuccessMessage> responseObserver){
//        userValidator.validateUserInput(request);
        //Creating problem while running
        userStore.put(request.getId(),request);

        UserProto.SuccessMessage successMessage= UserProto.SuccessMessage.newBuilder()
                .setMessage("User Created").build();
        responseObserver.onNext(successMessage);
        responseObserver.onCompleted();
    }
    @Override
    public void getUser(UserProto.GetUserRequest request, StreamObserver<UserProto.User> responseObserver){
        UserProto.User user=userStore.get(request.getId());
        if (user != null) {
            responseObserver.onNext(user);
        } else {
            responseObserver.onError(new Exception.UserNotFoundException("User not found!"));
        }
        responseObserver.onCompleted();
    }


    @Override
    public void listUsers(com.google.protobuf.Empty request, StreamObserver<UserProto.UserList> responseObserver){
        UserProto.UserList userList= UserProto.UserList.newBuilder().addAllUsers(userStore.values()).build();
        responseObserver.onNext(userList);
        responseObserver.onCompleted();
    }

}

