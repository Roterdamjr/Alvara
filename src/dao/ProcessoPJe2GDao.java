package dao;

import java.sql.SQLException;

import jdbc.DaoBasePJe1G;
import jdbc.DaoBasePJe2G;

public class ProcessoPJe2GDao extends DaoBasePJe2G{
		
	public String[] buscaDados(String proc, String sistema) throws Exception{
		String[] array = new String[6];
		
				
		String query = "select " +  
					"	case af.nm_agrupamento_fase  " + 
					"		when 'Arquivados' then 'Arquivado' " + 
					"		when 'Finalizados' then 'Finalizado' " + 
					"		WHEN 'Não classificado' THEN 'NCL' " + 
					"		else  'Em andamento' " + 
					"	end as situacao, " +
					" af.nm_agrupamento_fase ," +
					" (SELECT   oj.ds_orgao_julgador || ' - ' ||  taskinst.name_ 	localizacao " + 		
					"	FROM jbpm_taskinstance taskinst  " + 
					"	JOIN tb_tarefa_jbpm tj ON tj.id_jbpm_task::integer = taskinst.task_ " + 
					" JOIN jbpm_task task                      ON task.id_ = taskinst.task_" +
					"	JOIN jbpm_processdefinition procdef      ON procdef.id_ = task.processdefinition_ " + 
					" JOIN tb_processo_instance procinstcore   ON procinstcore.id_proc_inst = taskinst.procinst_" +
					"	JOIN tb_processo proc                    ON proc.id_processo::integer = procinstcore.id_processo " + 
					"	JOIN tb_processo_trf proctrf             ON proctrf.id_processo_trf::integer = proc.id_processo::integer " +  
					"	inner join pje.tb_orgao_julgador oj   on oj.id_orgao_julgador = proctrf.id_orgao_julgador " + 
					"	where proctrf.id_processo_trf=pr.id_processo 	          " + 
					"	order by taskinst.create_ desc " + 
					"	limit 1), " +
					" (select  DS_TEXTO_FINAL_EXTERNO " +
							" from pje.tb_processo proc"   +
							" left join pje.tb_processo_evento pe on pe.id_processo=proc.id_processo " +
							" left join pje.tb_evento e on e.id_evento=pe.id_evento " +
							" where proc.id_processo=pr.id_processo " +
							" order by  dt_atualizacao desc limit 1) ult_movimento " +
					" from pje.tb_processo pr " +  
					" left join pje_jt.tb_agrupamento_fase af on af.id_agrupamento_fase = pr.id_agrupamento_fase " + 
					" where af.nm_agrupamento_fase <> 'Elaboração' " + 
					" and nr_processo ='" +proc.trim() + "'";		
		
		executaBusca(query);

		try {
			array[0]=proc;
			while (rs.next()) {
				
				array[1]=rs.getString(1);
				array[2]=rs.getString(2);
				array[3]=rs.getString(3);
				array[4]=sistema;
				array[5]=rs.getString(4);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
	

	
	
}
