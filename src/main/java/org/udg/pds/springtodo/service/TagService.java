package org.udg.pds.springtodo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.pds.springtodo.dto.TagDto;
import org.udg.pds.springtodo.dto.TagRequest;
import org.udg.pds.springtodo.exception.ResourceNotFoundException;
import org.udg.pds.springtodo.mapper.TagMapper;
import org.udg.pds.springtodo.model.Tag;
import org.udg.pds.springtodo.repository.TagRepository;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Transactional(readOnly = true)
    public TagDto getTag(Long id) {
        Tag tag = getTagEntity(id);
        return tagMapper.tagToTagDto(tag);
    }

    @Transactional(readOnly = true)
    public List<TagDto> getTags() {
        return tagMapper.toTagDtoList(tagRepository.findAll());
    }

    @Transactional
    public TagDto addTag(TagRequest request) {
        String description = request.description() != null ? request.description() : "";
        Tag tag = new Tag(request.name(), description);
        tagRepository.save(tag);
        return tagMapper.tagToTagDto(tag);
    }

    @Transactional
    public void deleteTag(Long tagId) {
        Tag tag = getTagEntity(tagId);
        tagRepository.delete(tag);
    }

    public Tag getTagEntity(Long id) {
        return tagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag does not exist"));
    }
}
