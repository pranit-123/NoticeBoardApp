package org.notice.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.*;

public class DBhelper {

	protected DBconfig db = DBconfig.getDBInstanace();

	protected Connection con = DBconfig.getConnection();
	protected PreparedStatement stmt = DBconfig.getStatement();
	protected ResultSet rs = DBconfig.getResultSet();

}
