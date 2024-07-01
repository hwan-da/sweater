package com.ssafy.sweater.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.sweater.model.dto.Thunder;
import com.ssafy.sweater.model.service.ThunderService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/thunder")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "ThunderRestController", description = "번개 관리")
public class ThunderRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	private final ThunderService thunderService;
	private final ObjectMapper objectMapper;

	@Autowired
	public ThunderRestController(ThunderService thunderService, ObjectMapper objectMapper) {
		this.thunderService = thunderService;
		this.objectMapper = objectMapper;
	}

	// 번개 전체 조회
	@GetMapping("/")
	public ResponseEntity<?> list() {
		
		List<Thunder> result = thunderService.selectAll();

		if (result == null || result.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Thunder>>(result, HttpStatus.OK);
	}

	// 번개 id로 조회(상세 조회)
	@GetMapping("/{thunderId}")
	public ResponseEntity<?> detail(@PathVariable("thunderId") int thunderId) {
		Thunder result = thunderService.readThunder(thunderId);
		
		if (result == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Thunder>(result, HttpStatus.OK);
	}

	// 닉네임으로 조회
	@GetMapping("/nickname/{nickname}")
	public ResponseEntity<?> getListByNickname(@PathVariable("nickname") String nickname) {
		List<Thunder> result = thunderService.getThunderByNickname(nickname);

		if (result == null || result.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Thunder>>(result, HttpStatus.OK);
	}

	// 태그로 조회
	@GetMapping("/tag/{tag}")
	public ResponseEntity<?> getListByTag(@PathVariable("tag") String tag) {
		List<Thunder> result = thunderService.getThunderByTag(tag);

		if (result == null || result.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Thunder>>(result, HttpStatus.OK);
	}

	// 지역으로 조회
	@GetMapping("/region/{region}")
	public ResponseEntity<?> getListByRegion(@PathVariable("region") String region) {
		List<Thunder> result = thunderService.getThunderByRegion(region);

		if (result == null || result.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Thunder>>(result, HttpStatus.OK);
	}
	
	// 좋아요 한 번개 가져오기
	@GetMapping("/heart/{userId}")
	public ResponseEntity<?> getListByHeart(@PathVariable("userId") String userId){
		
		List<Thunder> result = thunderService.getThunderByHeart(userId);
		
		if (result == null || result.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		//System.out.println(result);
		
		return new ResponseEntity<List<Thunder>>(result, HttpStatus.OK);
	}
	
	// 게시글 번개 확인
		@PostMapping("/checkheart/{userId}/{thunderId}")
		public ResponseEntity<?> checkHeart(@PathVariable("userId") String userId, @PathVariable("thunderId") int thunderId) {
			System.out.println("오냐?");
			boolean isHeart = thunderService.checkHeart(thunderId, userId);
			
			System.out.println(isHeart);
			
			if(!isHeart) return new ResponseEntity<Boolean>(false, HttpStatus.OK);
			
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}

	// 번개 생성
	@PostMapping("/create")
	public ResponseEntity<?> write(@RequestParam("media") MultipartFile multipartFile, @RequestParam("thunder") String json) {
		//System.out.println("오냐");
		if(multipartFile == null || multipartFile.getSize() == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
		
		try {
			Thunder thunder = objectMapper.readValue(json, Thunder.class);
			//System.out.println(thunder);
			
			String fileName = thunderService.fileUpload(multipartFile);
			//System.out.println(fileName);
			
			thunder.setMedia(fileName);
			System.out.println(thunder.getTrail());
			
			int result = thunderService.writeThunder(thunder);
			
			if (result == 0) {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
	}

	// 번개 수정
	@PutMapping("/update")
	public ResponseEntity<?> modify(@RequestBody Thunder thunder) {
		int result = thunderService.modifyThunder(thunder);

		if (result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	// 번개 삭제
	@DeleteMapping("/{thunderId}")
	public ResponseEntity<?> delete(@PathVariable("thunderId") int thunderId) {
		//System.out.println("아");
		int result = thunderService.removeThunder(thunderId);

		if (result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// 번개 좋아요
	// userId로 thunderHeart에 해당 thunderId가 있는지 확인
	// select 결과가 0 -> 좋아요 활성화시키기
	// select 결과가 1 -> 좋아요 비활성화시키기
	@PostMapping("/heart/{userId}/{thunderId}")
	public ResponseEntity<?> heart(@PathVariable("userId") String userId, @PathVariable("thunderId") int thunderId){
		//System.out.println("뭐가오나?");
		int isHeart = thunderService.isHeart(thunderId, userId);
		
		thunderService.pushHeart(thunderId, userId, isHeart);
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	

}
