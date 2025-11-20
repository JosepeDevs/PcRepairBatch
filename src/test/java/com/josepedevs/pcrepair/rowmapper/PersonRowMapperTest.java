package com.josepedevs.pcrepair.rowmapper;

import com.josepedevs.pcrepair.domain.enums.PersonColumnsEnum;
import com.josepedevs.pcrepair.domain.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonRowMapperTest {

    @Test
    void mapRow_GivenResultSet_ThenReturnsPerson() throws SQLException {
        final var rs = mock(ResultSet.class);
        when(rs.getString(PersonColumnsEnum.ID_USER.getColumnName())).thenReturn("user1");
        when(rs.getString(PersonColumnsEnum.NAME.getColumnName())).thenReturn("John Doe");
        when(rs.getString(PersonColumnsEnum.METADATA.getColumnName())).thenReturn("metadata");
        when(rs.getString(PersonColumnsEnum.NID_PASSPORT.getColumnName())).thenReturn("N123456");
        when(rs.getInt(PersonColumnsEnum.DELETED.getColumnName())).thenReturn(0);

        final RowMapper<Person> mapper = new PersonRowMapper();

        final var person = mapper.mapRow(rs, 1);

        assertEquals("user1", person.getIdUser());
        assertEquals("John Doe", person.getName());
        assertEquals("metadata", person.getMetadata());
        assertEquals("N123456", person.getNidPassport());
        assertEquals(0, person.getDeleted());

        verify(rs).getString(PersonColumnsEnum.ID_USER.getColumnName());
        verify(rs).getString(PersonColumnsEnum.NAME.getColumnName());
        verify(rs).getString(PersonColumnsEnum.METADATA.getColumnName());
        verify(rs).getString(PersonColumnsEnum.NID_PASSPORT.getColumnName());
        verify(rs).getInt(PersonColumnsEnum.DELETED.getColumnName());
    }

    @Test
    void mapRow_GivenSQLException_ThenThrows() throws SQLException {
        final var rs = mock(ResultSet.class);
        when(rs.getString(anyString())).thenThrow(new SQLException("test"));

        final RowMapper<Person> mapper = new PersonRowMapper();

        assertThrows(SQLException.class, () -> mapper.mapRow(rs, 1));
    }
}
