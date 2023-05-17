package com.example.borodavkin_java.repository;

import lombok.RequiredArgsConstructor;
import com.example.borodavkin_java.enums.EngineType;
import com.example.borodavkin_java.enums.TransmissionType;
import com.example.borodavkin_java.model.CarGuideResponse;
import com.example.borodavkin_java.model.MarkResponse;
import com.example.borodavkin_java.model.ModelResponse;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.OrderField;
import static org.jooq.generated.tables.Configuration.CONFIGURATION;
import static org.jooq.generated.tables.Generation.GENERATION;
import static org.jooq.generated.tables.Mark.MARK;
import static org.jooq.generated.tables.Model.MODEL;
import static org.jooq.generated.tables.Modification.MODIFICATION;
import static org.jooq.generated.tables.Specifications.SPECIFICATIONS;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CarRepository {
    private final DSLContext dsl;

    public List<MarkResponse> getMarkList(String markName, Boolean asc) {
        var condition = creteFilterCondition(null, null, null, markName);
        OrderField<String> orderField = Boolean.TRUE.equals(asc) ? MARK.NAME.asc() : MARK.NAME.desc();

        return dsl.selectDistinct(
                        MARK.ID,
                        MARK.NAME
                ).from(MARK)
                .innerJoin(MODEL).on(MARK.ID.eq(MODEL.MARK_ID))
                .innerJoin(GENERATION).on(MODEL.ID.eq(GENERATION.MODEL_ID))
                .innerJoin(CONFIGURATION).on(GENERATION.ID.eq(CONFIGURATION.GENERATION_ID))
                .innerJoin(MODIFICATION).on(CONFIGURATION.ID.eq(MODIFICATION.CONFIGURATION_ID))
                .innerJoin(SPECIFICATIONS).on(MODIFICATION.COMPLECTATION_ID.eq(SPECIFICATIONS.COMPLECTATION_ID))
                .where(condition)
                .orderBy(orderField)
                .fetchInto(MarkResponse.class);
    }

    public List<ModelResponse> getModelByMarkIdAndClientType(String markId, String modelName, Boolean asc) {
        var condition = creteFilterCondition(markId, null, modelName, null);
        OrderField<String> orderField = Boolean.TRUE.equals(asc) ? MODEL.NAME.asc() : MODEL.NAME.desc();

        return dsl.selectDistinct(
                        MODEL.ID,
                        MODEL.NAME,
                        MARK.ID.as("markId")
                ).from(MARK)
                .innerJoin(MODEL).on(MARK.ID.eq(MODEL.MARK_ID))
                .innerJoin(GENERATION).on(MODEL.ID.eq(GENERATION.MODEL_ID))
                .innerJoin(CONFIGURATION).on(GENERATION.ID.eq(CONFIGURATION.GENERATION_ID))
                .innerJoin(MODIFICATION).on(CONFIGURATION.ID.eq(MODIFICATION.CONFIGURATION_ID))
                .innerJoin(SPECIFICATIONS).on(MODIFICATION.COMPLECTATION_ID.eq(SPECIFICATIONS.COMPLECTATION_ID))
                .where(condition)
                .orderBy(orderField)
                .fetchInto(ModelResponse.class);
    }

    public List<CarGuideResponse> getCarGuideList(String markId, String modelId) {
        var condition = creteFilterCondition(markId, modelId, null, null);

        return dsl.selectDistinct(
                        SPECIFICATIONS.ENGINE_TYPE,
                        SPECIFICATIONS.VOLUME_LITRES,
                        SPECIFICATIONS.TRANSMISSION
                ).from(MARK)
                .innerJoin(MODEL).on(MARK.ID.eq(MODEL.MARK_ID))
                .innerJoin(GENERATION).on(MODEL.ID.eq(GENERATION.MODEL_ID))
                .innerJoin(CONFIGURATION).on(GENERATION.ID.eq(CONFIGURATION.GENERATION_ID))
                .innerJoin(MODIFICATION).on(CONFIGURATION.ID.eq(MODIFICATION.CONFIGURATION_ID))
                .innerJoin(SPECIFICATIONS).on(MODIFICATION.COMPLECTATION_ID.eq(SPECIFICATIONS.COMPLECTATION_ID))
                .where(condition)
                .fetch()
                .map( it ->
                        new CarGuideResponse(
                                EngineType.fromString(it.getValue(SPECIFICATIONS.ENGINE_TYPE)),
                                it.getValue(SPECIFICATIONS.VOLUME_LITRES),
                                TransmissionType.fromString(it.getValue(SPECIFICATIONS.TRANSMISSION))
                        )
                );
    }

    private Condition creteFilterCondition(
            String markId,
            String modelId,
            String modelName,
            String markName
    ) {

        Condition condition = DSL.trueCondition();
        if(markName != null){
            var likeStr = "%" + markName.trim() + "%";
            condition = condition.and(MARK.NAME.likeIgnoreCase(likeStr));
        }
        if(modelName != null) {
            var likeStr = "%" + modelName.trim() + "%";
            condition = condition.and(MODEL.NAME.likeIgnoreCase(likeStr));
        }

        if(markId != null) {
            condition = condition.and(MARK.ID.eq(markId));
        }
        if(modelId != null) {
            condition = condition.and(MODEL.ID.eq(modelId));
        }

        return condition;
    }
}
