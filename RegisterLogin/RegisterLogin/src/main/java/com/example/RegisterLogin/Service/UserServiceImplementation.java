package com.example.RegisterLogin.Service;


import com.example.RegisterLogin.Configuration.JwtTokenProvider;
import com.example.RegisterLogin.exceptions.UserException;
import com.example.RegisterLogin.modals.Account;
import com.example.RegisterLogin.modals.User;
import com.example.RegisterLogin.repository.AccountRepository;
import com.example.RegisterLogin.repository.UserRepository;
import com.example.RegisterLogin.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AccountRepository accountRepository;

    public UserServiceImplementation(UserRepository userRepository,JwtTokenProvider jwtTokenProvider) {

        this.userRepository=userRepository;
        this.jwtTokenProvider=jwtTokenProvider;

    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user=userRepository.findById(userId);

        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found with id "+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        System.out.println("user service");
        String email=jwtTokenProvider.getEmailFromJwtToken(jwt);

        System.out.println("email"+email);

        User user=userRepository.findByEmail(email);

        if(user==null) {
            throw new UserException("user not exist with email "+email);
        }
        System.out.println("email user"+user.getEmail());
        return user;
    }



    @Override
    public List<User> findAllUsers() {
        // TODO Auto-generated method stub
        return userRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public ResponseEntity<?> getAccount_Details(String email) {
        User UserDetails = userRepository.findByEmail(email);
        Account AccountDetails = new Account();
        long userAccID =UserDetails.getId();

        if(!accountRepository.existsByUserId(userAccID)){
            AccountDetails.setUser(UserDetails);
            AccountDetails.setUserEmail(UserDetails.getEmail());
            AccountDetails.setFullName(UserDetails.getFirstName()+UserDetails.getLastName());
            accountRepository.save(AccountDetails);
        }
            Account account =accountRepository.findByUserId(userAccID);
            AccountResponse accountResponse =new AccountResponse();
            accountResponse.setFullName(account.getFullName());
            accountResponse.setUserEmail(account.getUserEmail());
            accountResponse.setLocation(account.getLocation());
            accountResponse.setPhoneNumber(account.getPhoneNumber());
            return ResponseEntity.ok(accountResponse);
        }

    @Override
    public ResponseEntity<?> updateAccount_Details(String email,Account userAccount) {
        User currentUser =userRepository.findByEmail(email);
        long currentUserId =currentUser.getId();

        try {
            if (accountRepository.existsByUserId(currentUserId) )
            {
                Account currentAccountDetails = accountRepository.findByUserId(currentUserId);
                currentAccountDetails.setFullName(userAccount.getFullName());
                currentAccountDetails.setPhoneNumber(userAccount.getPhoneNumber());
                currentAccountDetails.setLocation(userAccount.getLocation());
                currentAccountDetails.setUserEmail(userAccount.getUserEmail());
                accountRepository.save(currentAccountDetails);

            }
            return getAccount_Details(email);
        }
        catch(Exception e){
            return ResponseEntity.ok(e);
        }


    }


}


