package com.example.ecommerce.oauth;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.OAuthProviders;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.OAuthProvidersRepo;
import com.example.ecommerce.repository.UserRepo;
import com.example.ecommerce.service.MailService;

@Service
public class ProcessOAuth2UsersService {

	 private UserRepo userrepo;
	 private OAuthProvidersRepo oauthrepo;
	 private MailService mailservice;
	 
	 public ProcessOAuth2UsersService(UserRepo userrepo,OAuthProvidersRepo oauthrepo,
			 @Lazy MailService mailservice) {
		 this.userrepo = userrepo;
		 this.oauthrepo = oauthrepo;
		 this.mailservice = mailservice;
	 }
	
	 public void processOAuthUsers(String email,String name,String provider,String providerid) {
		 Users user = userrepo.findByEmail(email);
         
         if(user == null) {
 			Users newuser = new Users();
 			newuser.setName(name);
 			newuser.setEmail(email);
 			newuser.setAddress(null);
 			newuser.setPassword(null);
 			newuser.setProfileCompleted(true);
 			Users saveduser = userrepo.save(newuser);
 			
 			OAuthProviders oauthprovider = new OAuthProviders();
 			oauthprovider.setProvider(provider);
 			oauthprovider.setProviderid(providerid);
 			oauthprovider.setUserid(saveduser);
 			oauthrepo.save(oauthprovider);
 			mailservice.accountCreationMail(saveduser);
 		}
         
         else {
 			user.setName(name);
 			userrepo.save(user);
 			
 			OAuthProviders existprovider = oauthrepo.findByProviderAndProviderid(provider, providerid);
 			if(existprovider == null) {
 				
 				OAuthProviders oauthprovider = new OAuthProviders();
                 oauthprovider.setProvider(provider);
                 oauthprovider.setProviderid(providerid);
                 oauthprovider.setUserid(user);

                 oauthrepo.save(oauthprovider);
 			}
 		}
	 } 
}