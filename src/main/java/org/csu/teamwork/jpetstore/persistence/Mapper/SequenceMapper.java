package org.csu.teamwork.jpetstore.persistence.Mapper;


import org.csu.teamwork.jpetstore.domain.extra.Sequence;

public interface SequenceMapper {

    Sequence getSequence(Sequence sequence);

    void updateSequence(Sequence sequence);
}
