SELECT A.id, B.id, C.id, A.m, A.o, B.i
    FROM TABLE_A A
    JOIN TABLE_B B ON A.id = B.a_id
    JOIN TABLE_C C ON A.id = C.a_id
    WHERE A.n BETWEEN -50 AND 50
    AND C.x > 2000;