CREATE TABLE tbl_lancamento(
                _idLancamento INTEGER primary key autoincrement,
                tipoLancamento TEXT,
                valor DOUBLE,
                data DATE,
                descricao TEXT,
                idCategoria INTEGER,
                FOREIGN KEY(idCategoria) REFERENCES tbl_categoria(_idCategoria));

CREATE TABLE tbl_categoria(
                _idCategoria INTEGER primary key autoincrement,
                nomeCategoria TEXT));