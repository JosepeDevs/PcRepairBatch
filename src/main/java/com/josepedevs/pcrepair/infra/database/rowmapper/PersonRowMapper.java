package com.josepedevs.pcrepair.infra.database.rowmapper;

import com.josepedevs.pcrepair.domain.enums.PersonColumnsEnum;
import com.josepedevs.pcrepair.domain.model.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Person.builder()
                .idUser(rs.getString(PersonColumnsEnum.ID_USER.getColumnName()))
                .name(rs.getString(PersonColumnsEnum.NAME.getColumnName()))
                .metadata(rs.getString(PersonColumnsEnum.METADATA.getColumnName()))
                .nidPassport(rs.getString(PersonColumnsEnum.NID_PASSPORT.getColumnName()))
                .deleted(rs.getInt(PersonColumnsEnum.DELETED.getColumnName()))
                .build();
    }
}
