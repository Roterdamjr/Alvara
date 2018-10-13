package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Advogado;
import modelo.Parte;
import modelo.Processo;
import modelo.Setor;
import jdbc.DaoBasePJe1G;

public class ProcessoPJe1GDao extends DaoBasePJe1G {
	
	public Processo buscaDadosDoProcessoParaAlvara(String numeroCnjFormatado)
			throws Exception {
		
		Processo processo=null;
/*

		String query = "select ds_sigla from  pje.tb_processo pr " +   
		" left join tb_hist_desloca_oj h on id_processo=id_processo_trf " + 
			" join pje.tb_orgao_julgador oj on oj.id_orgao_julgador=h.id_oj_origem " +  
				" where nr_processo ='" +numeroCnjFormatado +"'" + 
				" order by dt_deslocamento desc " + 
				" limit 1 " ;*/
		
		String query = " 	with tt as (	 "+ 
		" 	select 	 "+ 
		" 	(select ds_sigla sigla1 from  pje.tb_processo pr  	 "+ 
		" 	left join tb_hist_desloca_oj h on id_processo=id_processo_trf  	 "+ 
		" 	left join pje.tb_orgao_julgador oj on oj.id_orgao_julgador=h.id_oj_origem  	 "+ 
		" 	where nr_processo ='"+numeroCnjFormatado+ "' order by dt_deslocamento desc  limit 1 ),	 "+ 
		" 	(select oj.ds_sigla sigla2 from pje.tb_processo pr 	 "+ 
		" 	 join pje.tb_processo_trf ptrf on ptrf.id_processo_trf=pr.id_processo	 "+ 
		" 	 join pje.tb_orgao_julgador oj on oj.id_orgao_julgador=ptrf.id_orgao_julgador	 "+ 
		" 	 where nr_processo = '" + numeroCnjFormatado+ "')	 "+ 
		" 	)	 "+ 
		" 	select coalesce(sigla1,sigla2) from tt 	";

		System.out.println(query);
		
		executaBusca(query);

		boolean achou = false;

		try {
			while (rs.next()) {
				processo = new Processo();
				Setor s =new Setor(); 
				s.setSiglaSetor(rs.getString(1));
				processo.setSetor(s);
				achou = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (achou) {
			
			processo.setNumCnj(numeroCnjFormatado);
			for (Advogado adv : buscaDadosDosAdvogados(numeroCnjFormatado)) {
				processo.addAdvogado(adv);
			}
			for (Parte pt : buscaDadosDasPartes(numeroCnjFormatado)) {
				processo.addParte(pt);
			}
			return processo;

		} else
			return null;

	}

	public ArrayList<Advogado> buscaDadosDosAdvogados(String numeroCnjFormatado)
			throws Exception {

		ArrayList<Advogado> lista = new ArrayList<Advogado>();

		// advogados
/*		String query = "  select (select upper(ul.ds_nome)"
				+ "          from tb_usuario_login ul"
				+ "         where ul.id_usuario = pp.id_pessoa) as nome,"
				+ "       (select (u.cd_estado || pa.nr_oab || pa.ds_letra_oab) oab"
				+ "          from tb_pessoa_advogado pa"
				+ "          join tb_estado u"
				+ "            on u.id_estado = pa.id_uf_oab"
				+ "           and pa.id = pp.id_pessoa"
				+ "         order by pa.dt_cadastro desc limit 1)"
				+ "  from tb_processo_parte pp" + "  left join tb_processo p"
				+ "    on p.id_processo = pp.id_processo_trf"
				+ " where p.nr_processo = '" + numeroCnjFormatado
				+ "'   and id_tipo_parte = 7" + " order by pp.in_participacao ";*/

		String query = " 	select distinct              	 "+ 
		" 	                u.ds_nome ,                	 "+ 
		" 	                (concat(coalesce((select cd_estado	 "+ 
		" 	                                   from tb_estado e	 "+ 
		" 	                                  where e.id_estado = adv.id_uf_oab),	 "+ 
		" 	                                 ''),	 "+ 
		" 	                        coalesce(adv.nr_oab, ''),	 "+ 
		" 	                        coalesce(adv.ds_letra_oab, ''))) as OAB  	 "+ 
		" 	  from tb_processo_trf p	 "+ 
		" 	 inner join tb_processo pc ON pc.id_processo::integer = p.id_processo_trf::integer	 "+ 
		" 	 inner join tb_processo_parte pp on pp.id_processo_trf = p.id_processo_trf	 "+ 
		" 	 inner join tb_tipo_parte tp on tp.id_tipo_parte = pp.id_tipo_parte	 "+ 
		" 	 inner join tb_usuario_login u ON u.id_usuario = pp.id_pessoa::integer	 "+ 
		" 	 inner join tb_pessoa pe on pe.id_pessoa = pp.id_pessoa	 "+ 
		" 	  left join tb_pessoa_advogado adv on adv.id = pp.id_pessoa	 "+ 
		" 	 where pc.nr_processo = '" + numeroCnjFormatado+ "' "+ 
		" 	and ds_tipo_parte='ADVOGADO'	 "; 

		
		
		executaBusca(query);

		try {
			while (rs.next()) {
				Advogado advogado = new Advogado();
				advogado.setNome(rs.getString(1));
				advogado.setOAB(rs.getString(2));
				lista.add(advogado);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<Parte> buscaDadosDasPartes(String numeroCnjFormatado)
			throws Exception {

		ArrayList<Parte> lista = new ArrayList<Parte>();

		// partes
		String query = "  select (select ul.ds_nome"
				+ "		          from tb_usuario_login ul"
				+ "		         where ul.id_usuario = pp.id_pessoa) as nome,"
				+ "		       (select pdi.nr_documento_identificacao"
				+ "		          from tb_pess_doc_identificacao pdi"
				+ "		         where pdi.cd_tp_documento_identificacao in ('CPF', 'CPJ', 'RJC')"
				+ "		           and in_principal = 'S'"
				+ "		           and pdi.id_pessoa = pp.id_pessoa"
				+ "		         order by id_pessoa_doc_identificacao limit 1) documento"
				+ "      		  from tb_processo_parte pp"
				+ "		  left join tb_processo p"
				+ "		    on p.id_processo = pp.id_processo_trf"
				+ "		 where p.nr_processo = '" + numeroCnjFormatado
				+ "'		and id_tipo_parte <> 7" + " order by pp.in_participacao";

		executaBusca(query);

		try {
			while (rs.next()) {
				Parte parte = new Parte();
				parte.setNome(rs.getString(1));
				parte.setCPF(rs.getString(2));
				lista.add(parte);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

}
