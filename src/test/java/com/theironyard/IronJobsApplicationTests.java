package com.theironyard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.command.UserCommand;
import com.theironyard.entities.Location;
import com.theironyard.entities.Posting;
import com.theironyard.entities.User;
import com.theironyard.services.LocationRepository;
import com.theironyard.services.PostingRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IronJobsApplicationTests {

	@Autowired
	WebApplicationContext wac;

	@Autowired
	UserRepository testUsersRepo;

	@Autowired
	PostingRepository testPostingsRepo;

	@Autowired
	LocationRepository testLocationsRepo;

	MockMvc mockMvc;

	@Before
	public void before(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/***** POST & PUT Endpoints *****/
	@Test
	public void testLogin() throws Exception {
		User user = new User("Nigel", PasswordStorage.createHash("password"));

		UserCommand uc = new UserCommand();
		uc.setUsername(user.getName());
		uc.setPassword("password");

		ObjectMapper OM = new ObjectMapper();
		String json = OM.writeValueAsString(uc);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/users")
				.content(json)
				.contentType("application/json")
		).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void checkTokenInfo() throws Exception {

		User user = testUsersRepo.findFirstByName("Nigel");

		UserCommand uc = new UserCommand();
		uc.setUsername(user.getName());
		uc.setPassword("password");


		ObjectMapper OM = new ObjectMapper();
		String json = OM.writeValueAsString(uc);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/token")
				.content(json)
				.contentType("application/json")
		).andReturn();
		String token = user.getToken();
		assertTrue("Token not created", user.getToken().equals(token));
	}

	@Test
	public void checkTokenRegen() throws Exception {
		User user = new User("Nigel", PasswordStorage.createHash("password"));

		UserCommand uc = new UserCommand();
		uc.setUsername(user.getName());
		uc.setPassword("password");

		ObjectMapper OM = new ObjectMapper();
		String json = OM.writeValueAsString(uc);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/token/regenerate")
						.content(json)
						.contentType("application/json")
		).andExpect(status().is4xxClientError());
	}

	@Test
	public void testCreatePosting() throws Exception {
		Location location = new Location("henderson", "NV");
		Posting posting = new Posting("new", "posting", 1, 2, location);
		posting.setLocation(location);

		testLocationsRepo.save(location);
		testPostingsRepo.save(posting);

		ObjectMapper objmap = new ObjectMapper();
		String json = objmap.writeValueAsString(posting);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/postings")
						.content(json)
						.contentType("application/json")
		).andReturn();
		assertTrue("no postings in database", testPostingsRepo.count() >= 1);
	}

	/***** GET Endpoints *****/
	@Test
	public void testGetAllPostings() throws Exception{
		Location location = new Location("henderson", "NV");
		Posting posting = new Posting("new", "posting", 1, 2, location);

		testLocationsRepo.save(location);
		testPostingsRepo.save(posting);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/postings")
		).andReturn();
		assertTrue("no postings to return", testPostingsRepo.count() >= 1);
	}

	@Test
	public void testGetAllUsers() throws Exception{
		User user = new User("Jeff", PasswordStorage.createHash("password"));
		testUsersRepo.save(user);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/users")
		).andReturn();
		assertTrue("no users to return", testUsersRepo.count() >= 1);
	}
}
