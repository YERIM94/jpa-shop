package jpabook.jpashop.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController //data자체를 바로 json이나 xml로 보낼 때 
@RequiredArgsConstructor
public class MemberApiController {

	private final MemberService memberService;

	@GetMapping("/api/v1/members")
	public List<Member> membersV1(){
		return memberService.findMembers();
	}
	
	@GetMapping("/api/v2/members")
	public Result membersV2(){
		List<Member> findMembers = memberService.findMembers();
		
		//ver1
		List<MemberDto> resultMembers = new ArrayList<MemberDto>();
		
		for(int i=0; i<findMembers.size();i++) {
			resultMembers.add(new MemberDto(findMembers.get(i).getName()));
		}
		
		//ver2
		//List<MemberDto> collect = findMembers.stream().map(m->new MemberDto(m.getName())).collect(Collectors.toList());
		
		return new Result(resultMembers);
	}
	
	@Data
	@AllArgsConstructor
	static class Result<T>{
		private T data;
	}
	
	@Data
	@AllArgsConstructor
	static class MemberDto{
		private String name;
	}
	
	@PostMapping("/api/v1/members")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}
	
	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
		//entity를 직접 파라미터로 받지않고 새로 생성해서 넘겨줌 
		Member member = new Member();
		member.setName(request.getName());
		
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}
	
	@PutMapping("/api/v2/members/{id}")
	public UpdateMemberResponse updateMemberV2(
			@PathVariable("id") Long id,
			@RequestBody @Valid UpdateMemberRequest request) {
		memberService.update(id, request.getName());
		Member findMember = memberService.findOne(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName());
	}
	
	@Data
	@AllArgsConstructor
	static class UpdateMemberResponse{
		private Long id;
		private String name;
	}
	
	@Data
	static class UpdateMemberRequest{
		private String name;
	}
	
	@Data
	static class CreateMemberResponse{
		private Long id;

		public CreateMemberResponse(Long id) {
			this.id = id;
		}
	}
	
	@Data
	static class CreateMemberRequest{
		private String name;
	}
}