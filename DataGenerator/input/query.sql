SELECT * 
	FROM TABLE_A A 
	join TABLE_B B on A.n > B.a_n 
	WHERE A.name = 'XXXXX'
	AND B.name <> 'YYY';