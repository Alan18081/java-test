package com.alex;

import org.junit.Before;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Mockito.mock;

public class StudentProviderTest {
    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;

    @Before
    public void before() {
        c = mock(Connection.class);
        ps = mock(PreparedStatement.class);

    }

}
