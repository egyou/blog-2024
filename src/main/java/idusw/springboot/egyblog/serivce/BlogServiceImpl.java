package idusw.springboot.egyblog.serivce;

import idusw.springboot.egyblog.entity.BlogEntity;
import idusw.springboot.egyblog.entity.MemberEntity;
import idusw.springboot.egyblog.model.BlogDto;
import idusw.springboot.egyblog.repository.BlogRepository;
import idusw.springboot.egyblog.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    final BlogRepository blogRepository;
    final MemberRepository memberRepository;
    public BlogServiceImpl(BlogRepository blogRepository, MemberRepository memberRepository) {
        this.blogRepository = blogRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public int create(BlogDto dto) {
        blogRepository.save(dtoToEntity(dto)); // insert into blog values ....;
        return 1;
    }

    @Override
    @Transactional
    public BlogDto read(BlogDto dto) { // BlogDto = BlogEntity + MemberEntity
        Optional<BlogEntity> blogEntityOptional = blogRepository.findById(dto.getIdx());
        // Optional<BlogEntity> entity = blogRepository.findByIdx(dto);
        Optional<MemberEntity> memberEntityOptional =
                memberRepository.findByIdx(blogEntityOptional.get().getBlogger().getIdx());
        return entityToDto(blogEntityOptional.get(), memberEntityOptional.get());
    }

    @Override
    public List<BlogDto> readList() {
        // DTO : Controller, DTO/Entity : Service, Entity : Repository
        List<BlogEntity> blogEntityList = blogRepository.findAll(); // select * from blog;
        List<BlogDto> blogDtoList = new ArrayList<>();
        for(BlogEntity blogEntity : blogEntityList) { // JCF & Builder Pattern
            MemberEntity memberEntity = MemberEntity.builder()
                    .idx(blogEntity.getBlogger().getIdx())
                    .build();
            blogDtoList.add(entityToDto(blogEntity, memberEntity));
        }
        return blogDtoList;
    }

    @Override
    public int update(BlogDto dto) {
        return 0;
    }

    @Override
    public int delete(BlogDto dto) {
        blogRepository.delete(dtoToEntity(dto)); // delete from blog where idx = ***;
        return 0;
    }
}
