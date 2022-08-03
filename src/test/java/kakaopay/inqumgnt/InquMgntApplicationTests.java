package kakaopay.inqumgnt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import kakaopay.inquMgnt.controller.InquMgntController;
import kakaopay.inquMgnt.repository.ClserRepository;
import kakaopay.inquMgnt.repository.InquRepository;
import kakaopay.inquMgnt.repository.UserRepository;
import kakaopay.inquMgnt.util.GsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class InquMgntApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	ClserRepository clserRepository;
	@Autowired
	InquRepository inquRepository;
	@Autowired
	UserRepository userRepository;

	public void setup() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(InquMgntController.class).build();
	}

	  /* 문의내역 저장
     @param userId 고객ID
     @param inquTitl 문의제목
     @param inquCn 문의내용
     */
	@Test
	void insertInquTest() throws Exception{
		// 요청값 세팅
		RequestDTO inquMgnt = new RequestDTO();
		inquMgnt.userId = "jin";
		inquMgnt.inquTitl = "택배가 오지 않아요";
		inquMgnt.inquCn = "3일전에 주문했는데 택배가 아직 도착하지 않았어요";

		// Json타입으로 파라미터 입력
		GsonUtils gson = new GsonUtils();
		String resJson = gson.toJson(inquMgnt);

		mockMvc.perform(
						post("/kakaopay/inqu")
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(resJson))
		).andDo(print());
	}

}

@DataJdbcTest
class RequestDTO {
	String userId;
	String inquTitl;
	String inquCn;
}
