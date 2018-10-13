--partes
select (select ul.ds_nome
          from tb_usuario_login ul
         where ul.id_usuario = pp.id_pessoa) as nome,
       (select pdi.nr_documento_identificacao
          from tb_pess_doc_identificacao pdi
         where pdi.cd_tp_documento_identificacao in ('CPF', 'CPJ', 'RJC')
           and in_principal = 'S'
           and pdi.id_pessoa = pp.id_pessoa
         order by id_pessoa_doc_identificacao limit 1) documento       
  from tb_processo_parte pp
  left join tb_processo p
    on p.id_processo = pp.id_processo_trf
 where p.nr_processo = '0010000-19.2012.5.01.0541'
and id_tipo_parte <> 7
order by pp.in_participacao
 
--advogados
  select (select upper(ul.ds_nome)
          from tb_usuario_login ul
         where ul.id_usuario = pp.id_pessoa) as nome,
       (select (u.cd_estado || pa.nr_oab || pa.ds_letra_oab) oab
          from tb_pessoa_advogado pa
          join tb_estado u
            on u.id_estado = pa.id_uf_oab
           and pa.id = pp.id_pessoa
         order by pa.dt_cadastro desc limit 1)
  from tb_processo_parte pp
  left join tb_processo p
    on p.id_processo = pp.id_processo_trf
 where p.nr_processo = '0010000-19.2012.5.01.0541'
   and id_tipo_parte = 7
 order by pp.in_participacao

select * from tb_pessoa_advogado limit 10