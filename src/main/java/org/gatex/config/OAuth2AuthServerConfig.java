package org.gatex.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {


	private AuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;
	private String SIGNING_KEY;

	public OAuth2AuthServerConfig(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, @Value("${jwt.symmetric.signing.key}") String SIGNING_KEY) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.SIGNING_KEY = SIGNING_KEY;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints
			.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter()) 
			.authenticationManager(authenticationManager);
	}
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		    clients
			    .inMemory()
				.withClient("gatexui")
				.secret(passwordEncoder.encode("secret"))
				.authorizedGrantTypes("password")
				.scopes("NA")
				.accessTokenValiditySeconds(86400); 
	}
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter()); 
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(SIGNING_KEY); 
		return converter;
	}
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}
}
