package it.uniroma3.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.model.Responsabile;
import it.uniroma3.repository.ResponsabileRepository;

@Transactional
@Service
public class ResponsabileService implements UserDetailsService{

	@Autowired
	private ResponsabileRepository responsabileRepository;

	@Autowired
	private PasswordEncoder pwEncode;

	public Responsabile save(Responsabile responsabile) {
		responsabile.setPassword(pwEncode.encode(responsabile.getPassword()));
		return this.responsabileRepository.save(responsabile);
	}

	public List<Responsabile> findAll(){
		return (List<Responsabile>)this.responsabileRepository.findAll();
	}
	public Responsabile findById(Long id) {
		Optional<Responsabile> responsabile = this.responsabileRepository.findById(id);
		if (responsabile.isPresent()) 
			return responsabile.get();
		else
			return null;
	}
	public boolean alreadyExists(Responsabile responsabile) {
		List<Responsabile> responsabili = this.responsabileRepository.findByNomeAndCognome(responsabile.getNome(),responsabile.getCognome());
		if ((responsabili.size() > 0))//controllo capienza
			return true;
		else 
			return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Responsabile user = this.responsabileRepository.findByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException("Username not found");

		return new org.springframework.security.core.userdetails.User(username,user.getPassword()
				,Collections.singleton(new SimpleGrantedAuthority("user")));
	}

	public Responsabile findByUsername(String username) {
		return this.responsabileRepository.findByUsername(username);
	}

}