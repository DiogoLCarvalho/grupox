package com.fatec.sig1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fatec.sig1.model.Ong;
import com.fatec.sig1.model.Admin;
import com.fatec.sig1.model.Comentario;
import com.fatec.sig1.model.Exclusao;
import com.fatec.sig1.model.MantemAdminRepository;
import com.fatec.sig1.model.MantemComentarioRepository;
import com.fatec.sig1.model.MantemExclusaoRepository;
import com.fatec.sig1.model.MantemOngRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fatec.sig1.model.User;
import com.fatec.sig1.model.MantemUserRepository;

@Configuration 
public class LoadDatabase {
	Logger logger = LogManager.getLogger(this.getClass());
	private static final String TEXTO_LOGGER = "Preloading  %s";
	private static final String CATEGORIA_ONG_CID = "Cidadania";
	private static final String CNAE = "9430-8|00";
	private static final String ZONA_LESTE = "Zona Leste";
	private static final String ZONA_OESTE = "Zona Oeste";
	private static final String ZONA_NORTE = "Zona Norte";
	private static final String ZONA_SUL = "Zona Sul";
	private static final String CENTRO = "Centro";
	private static final String BANCO_ITAU = "Banco Itaú";
	
	Ong ong1 = new Ong("Adote sempre cabe mais um", 981151084, "03694000","Casa", "O Instituto Adote Sempre Cabe Mais Um resgata cães e gatos abandonados ou em situação de maus-tratos em Jarinu (SP). Os animais acolhidos são castrados, vacinados e encaminhados para a adoção, que também é acompanhada pela ONG.", "Proteção Animal", "adotesemprecabemaisum@gmail.com", "$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.", "33.605.926/0001-60", "6612-6|05", "089527", "0191", "Bradesco","emailpixadote@gmail.com", "302.206.482-71",ZONA_NORTE,LocalDate.parse("2023-02-20"));
	
	List<Long> user2Favoritos = new ArrayList<>(Arrays.asList((long) 1, (long) 5));
	User user2 = new User("Bianca", "Jesus","biancaJesus299@gmail.com", "$2a$10$xbQPb0a0jt3k2oSa2R3wFO2KmVxLT2XNao7mC/hKGcRe/o27/JzDy", LocalDate.parse("2023-05-21"),user2Favoritos);
	
	@Autowired
	MantemOngRepository ongRepository;
	
	@Bean
	CommandLineRunner initDatabase(MantemOngRepository repository, MantemOng repoCliente) {
	return args -> {
		repository.deleteAll();

		ong1.setEndereco("Aguia de Haia");
		logger.info(TEXTO_LOGGER, repository.save(ong1));
		
		Ong ong2 = new Ong
				(
						"Projeto Esperança", 
						1106944784, 
						"08011030",
						"Casa", 
						"O Projeto Esperança de São Miguel Paulista - PROJESP - foi criado a partir de uma grande necessidade de atuação social na década de 80 para a assistência às pessoas vítimas de uma forte epidemia de AIDS. Pela falta de informações, recursos financeiros e os devidos direcionamentos, a população acometida pela doença nessa região era uma das mais fragilizadas.", 
						CATEGORIA_ONG_CID, 
						"projesp@projesp.org.br", "$2a$10$ZWj75XyUg.hYBS1SPIisWudZJQJSCBpP9JJXgy5ZzET2UsjHuIF9W", 
						"12.121.323/0002-61", 
						CNAE, 
						"12212", 
						"0154", 
						"Bradesco",
						"esperanzaProjeto@gmail.com", 
						"102.206.482-71",
						ZONA_LESTE,
						LocalDate.parse("2023-02-25")
				);
		ong2.setEndereco("Guilherme de Aguiar");
		logger.info(TEXTO_LOGGER , repository.save(ong2));
		
		Ong ong3 = new Ong
				(
						"Associação Fala Mulher", 
						1130630807, 
						"05417000",
						"Casa", 
						"Somos uma Organização sem fins lucrativos, que atua no enfrentamento à violência doméstica contra a mulher, na construção da equidade de gênero, na promoção da independência financeira feminina, na facilitação do acesso às informações sobre a Lei Maria da Penha e os direitos das mulheres. Além do trabalho na defesa e garantia dos direitos humanos, de pessoas em situação de vulnerabilidade social ou violação de direitos.", 
						"Gênero e diversidade", 
						"falaMulher@hotmail.com", "$2a$10$MXOcfFeyLewG5W3dliPHcu7DyxFoRAfGF5V2m0igHjGhz4NvndzFC", 
						"11.111.333/0002-61", 
						CNAE, 
						"12200", 
						"0004", 
						"Nubank",
						"67084387776011847635893856138508", 
						"200.006.182-71",
						ZONA_OESTE,
						LocalDate.parse("2023-02-10")
				);
		ong3.setEndereco("Mourato Coelho");
		logger.info(TEXTO_LOGGER, repository.save(ong3));
		
		Ong ong4 = new Ong
				(
						"Grão da Vida", 
						1155231772, 
						"04775120",
						"Casa", 
						"O Grão da Vida é uma Instituição não governamental, que há mais de 30 anos promove o Desenvolvimento Infantil. Em convênio com a Prefeitura Municipal de São Paulo, atualmente respondemos pela educação de 290 crianças de 0 a 3 anos de idade em 2 Centros de Educação Infantil localizados nas regiões de Capela do Socorro (CEI Manoel Bispo dos Santos) e Grajaú (CEI Marina Villares da Silva Novaes)", 
						"Educação", 
						"GraoDaVida2000@gmail.com", "$2a$10$uqlJr0/EEaH0WD1VOgF6B.1YmLj7XsR/lz1eVmr0ydrt8iQ7zvYbO", 
						"44.111.343/1102-00", 
						CNAE, 
						"99200", 
						"1001", 
						BANCO_ITAU,
						"GraoDaVida2000@gmail.com", 
						"221.346.182-71",
						ZONA_SUL,
						LocalDate.parse("2023-04-01")
				);
		ong4.setEndereco("Olímpio Carr Ribeiro");
		logger.info(TEXTO_LOGGER , repository.save(ong4));
		
		
		Ong ong5 = new Ong
				(
						"Nós do Bem", 
						1140023892, 
						"01011100",
						"13° andar", 
						"A Associação Voluntários Nós do Bem é uma Organização Não Governamental (ou seja, privada e sem fins lucrativos), legalmente instituída com o objetivo de promover o desenvolvimento social. Nascida em 07 de maio de 2016, visa levar motivação da cultura e da arte nos locais que consiste na visita regular de nossos participantes nestes locais, onde promovem práticas de caráter lúdico, de contato humano.", 
						"Cultura e esporte", 
						"contato@nosdobem.org", "$2a$10$docvFH5qesVTXh.fWSKn1uRAGkaY/h7fXbx1g1sZZPEUjH9Vmtz4m", 
						"99.999.124/1102-00", 
						CNAE, 
						"91311", 
						"1002", 
						"Banco do Brasil",
						"contato@nosdobem.org", 
						"222.121.182-71",
						CENTRO,
						LocalDate.parse("2023-03-20")
				);
		ong5.setEndereco("Rua São Bento, 329");
		logger.info(TEXTO_LOGGER , repository.save(ong5));
		
		Ong ong6 = new Ong
				(
						"Kor Ambiental", 
						1199325819, 
						"04757000",
						"Casa", 
						"A Kor Ambiental foi fundada com objetivo de fornecer serviços de excelência em soluções de engenharia ambiental, focadas nas áreas "
						+ "de gerenciamento de áreas Contaminadas, "
						+ "Gerenciamento de áreas Verdes e Licenciamento Ambiental. "
						+ "Composta por uma equipe multidisciplinar"
						+ " de profissionais qualificados, graduados em instituições renomadas e com mais de 10 anos de experiência, a Kor Ambiental está pronta para oferecer soluções econômicas e sustentáveis de acordo com a necessidade do cliente, público ou privado, dos mais diversos setores.", 
						"Meio Ambiente", 
						"contato@korambiental.com.br", "$2a$10$DCJOylrfqb3Ac7fIjkUJlu2ZofBzbDx2DeUezTRJvArWGgD9.pKdO", 
						"11.399.124/1102-00", 
						CNAE, 
						"89450", 
						"1001", 
						"Banco Pan",
						"contato@korambiental.com.br", 
						"111.121.182-71",
						ZONA_SUL,
						LocalDate.parse("2023-04-18")
				);
		ong6.setEndereco("Bento Branco de Andrade Filho");
		logger.info(TEXTO_LOGGER , repository.save(ong6));
		
		Ong ong7 = new Ong
				(
						"Cuida de Mim", 
						1196366761, 
						"03511060",
						"Casa", 
						"O projeto do Instituto Cuida de Mim surgiu em 2007 a partir de reflexões e questionamentos de um grupo multidisciplinar de profissionais com experiência e prática clínica, obtidos no Instituto Sedes Sapientiae, bem como através de atividades em consultórios particulares e outras instituições.", 
						"Saúde", 
						"contato@cuidademim.com.br", "$2a$10$p7/J19o8h6uHmOg6HkDedOS5cN9Hj62JVcb3gyWeFc.A0dXMuXmj2", 
						"92.111.124/1102-00", 
						CNAE, 
						"112122", 
						"9901", 
						"Banco inter",
						"contatoPix@cuidademim.com.br", 
						"881.001.182-71",
						ZONA_LESTE,
						LocalDate.parse("2023-01-29")
				);
		ong7.setEndereco("Praça Dom Duarte Leopoldo, 137");
		logger.info(TEXTO_LOGGER , repository.save(ong7));
		
		Ong ong8 = new Ong
				(
						"Obra Social São Mateus", 
						1129195894, 
						"03959030",
						"Casa", 
						"Fundada em 09 de Setembro de 1964, nasceu da preocupação de um grupo da Comunidade de São Mateus com a população do bairro, que crescia gradativamente e desprovida de recursos mínimos necessários como: água, luz, saneamento básico, transporte e alimentação.", 
						CATEGORIA_ONG_CID, 
						"sede@obrasocialsaomateus.org", "$2a$10$AbJV23oS/Paek9LWa1pRQOo1QK7H0k2X6zVlvlrrhRdQBfTfAUB1m", 
						"93.111.124/1102-00", 
						CNAE, 
						"112125", 
						"9901", 
						"Banco Central",
						"saoMatheus@gmail.com", 
						"961.001.182-71",
						ZONA_LESTE,
						LocalDate.parse("2023-03-29")
				);
		ong8.setEndereco("Rua Alessandro Giulio Dell'Aringa, 126");
		logger.info(TEXTO_LOGGER, repository.save(ong8));
		
		Ong ong9 = new Ong
				(
						"Rosa Mulher - Grupo de Apoio - Câncer de Mama", 
						1120129634, 
						"03963050",
						"Casa", 
						"Somos uma equipe formada por mulheres com histórias diferentes. Entre nós, temos psicóloga, assistente social, mulheres que estão enfrentando a batalha pela vida, outras que passaram e venceram o câncer de mama e outras simpatizantes da causa. Acreditamos que nossos caminhos não se cruzaram por acaso, por isso, decidimos nos unir para dar apoio, carinho e atenção à MULHER que enfrenta a luta contra o câncer.", 
						"Gênero e diversidade", 
						"apoio@associacaorosamulher.org", "$2a$10$thhV99i.4mbH2MKldYomLu4lmTfF2JK6P81Jo6lc4Uj9ffPRDiYRy", 
						"94.111.124/1102-00", 
						CNAE, 
						"112129", 
						"9901", 
						"Santander",
						"contatoPix@rosaMulher.com.br", 
						"999.051.182-71",
						ZONA_LESTE,
						LocalDate.parse("2023-03-21")
				);
		ong9.setEndereco("R. Dr. José Cioffi, 475");
		logger.info(TEXTO_LOGGER , repository.save(ong9));
		
		Ong ong10 = new Ong
				(
						"Associação de apoio humanitário", 
						1120129634, //11997801429
						"04232000",
						"Casa", 
						"A Ong Associação de Apoio Humanitário, AAH, é uma organização criada com o intuito de arrecadar fundos e doações para famílias que estão passando por necessidades e também para moradores de rua.", 
						CATEGORIA_ONG_CID, 
						"contato@ongaah.com.br", "$2a$10$/.NH83lAcileuGjCsnOKj.af0M0le9UWAl6E1J2pK1DdE6/6qw6jy", 
						"95.111.124/1102-00", 
						CNAE, 
						"112199", 
						"9901", 
						"Santander",
						"contatoPix@apoioHumanitario.com.br", 
						"931.015.182-71",
						ZONA_LESTE,
						LocalDate.parse("2023-05-05")
				);
		ong10.setEndereco("Estr. das Lágrimas, 1712");
		logger.info(TEXTO_LOGGER, repository.save(ong10));
		
		Ong ong11 = new Ong
				(
						"ONG Banco de Alimentos", 
						1120129634, //11950290006
						"01235010",
						"Casa", 
						"Somos o resultado da visão pioneira de nossa fundadora, a economista Luciana Chinaglia Quintão, e a soma de esforços de cidadãos, empresas e indústrias alimentícias dedicadas a reduzir o desperdício de alimentos e a combater a fome.", 
						CATEGORIA_ONG_CID, 
						"vanessa.godoy@printeccomunicacao.com.br", "$2a$10$SsBSeh.7qzyQOodRtn3V3ucQFq7VAv8S/kxWALN9qN23m62D9kaiC", 
						"96.111.124/1102-00", 
						CNAE, 
						"110023", 
						"9901", 
						"Banco Original",
						"contatoPix@alimentos.com.br", 
						"091.000.182-71",
						ZONA_OESTE,
						LocalDate.parse("2023-05-20")
				);
		ong11.setEndereco("Rua Atibaia, 218");
		logger.info(TEXTO_LOGGER, repository.save(ong11));
		
		Ong ong12 = new Ong
				(
						"Agente Ajuda", 
						1121901465, //Não tem número de tel
						"04563013",
						"Casa", 
						"Somos uma causa.Somos um sentimento.Somos PESSOAS!", 
						CATEGORIA_ONG_CID, 
						"contato@agenteajuda.org.br", "$2a$10$kBA0cFNiMa1T3KasYhf0Zu.PR.dyeZhikcwCseTnnFaAQEuJ6areK", 
						"97.111.124/1102-00", 
						CNAE, 
						"000067", 
						"9901", 
						BANCO_ITAU,
						"agenteAjuda@hotmail.com", 
						"091.001.182-40",
						ZONA_SUL,
						LocalDate.parse("2023-03-02")
				);
		ong12.setEndereco("Av. Padre Antônio José dos Santos, 1140");
		logger.info(TEXTO_LOGGER, repository.save(ong12));
		
		Ong ong13 = new Ong
				(
						"A Make-A-Wish Brasil", 
						1120129634, //(11) 5081.3601
						"04583911",
						"Casa", 
						"É uma das 39 afiliadas da Make-A-Wish® International, uma das instituições de apoio à criança mais conhecidas e respeitadas no mundo.", 
						CATEGORIA_ONG_CID, 
						"enquiries@makeawish.org.au", "$2a$10$uXNwp7Yo8AssPqAVq/1NcewbpWt.VwI0YHCDHy09ctP./MDR11KVW", 
						"10.111.124/1102-00", 
						CNAE, 
						"112090", 
						"9901", 
						BANCO_ITAU,
						"4362061520", 
						"991.011.112-11",
						ZONA_SUL,
						LocalDate.parse("2023-01-30")
				);
		ong13.setEndereco("R. Álvaro Rodrigues, 152");
		logger.info(TEXTO_LOGGER, repository.save(ong13));
		
		Ong ong14 = new Ong
				(
						"Associação Crescer Sempre", 
						1120129634, //(11) 3744-8573
						"05660000",
						"Casa", 
						"Somos uma Associação sem fins lucrativos localizada na comunidade de Paraisópolis, em São Paulo, com cinco projetos principais: uma escola regular de Educação Infantil e de Ensino Fundamental II, uma escola integral de Ensino Médio; Cursos Profissionalizantes e Biblioteca aberta à comunidade.", 
						CATEGORIA_ONG_CID, 
						"contato@contato.org", "$2a$10$cHM4MmiitfC97.8joJMVZ.rpfX1alfWk7fCBrBdXqkXccWhwbIh.6", 
						"20.111.124/1102-00", 
						CNAE, 
						"002123", 
						"9901", 
						"Banco do Brasil",
						"36521565981", 
						"831.001.182-71",
						ZONA_SUL,
						LocalDate.parse("2023-05-07")
				);
		ong14.setEndereco("Rua Pasquale Gallupi, 928");
		logger.info(TEXTO_LOGGER, repository.save(ong14));
		
		Ong ong15 = new Ong
				(
						"APOIO – Associação de Auxílio Mútuo da Região Leste", 
						1120129634, //55 (11) 3361-5900
						"01214100",
						"Casa", 
						"APOIO – Associação de Auxílio Mútuo da Região Leste iniciou suas atividades em 1992  e foi fundada em 18/12/1993, organização civil sem fins lucrativos, reconhecida de Utilidade Pública Federal, resultou de um grupo formado por pessoas de vários segmentos sociais preocupados com a pobreza de amplas camadas populares. O contato permanente com famílias de baixa renda impulsionou a organização a participar em várias atividades de apoio às pessoas em situação de vulnerabilidade social.", 
						CATEGORIA_ONG_CID, 
						"apoio@apoio-sp.org.br", "$2a$10$7UVoYM9g.NKcPMi8mrUl0u9YF9xflwEuSVIru3Lnfv3pDgULUCWjy", 
						"30.111.124/1102-00", 
						CNAE, 
						"112329", 
						"9901", 
						"Caixa",
						"21078945871", 
						"821.001.182-71",
						ZONA_LESTE,
						LocalDate.parse("2023-05-09")
				);
		ong15.setEndereco("Av. Duque de Caxias, 325");
		logger.info(TEXTO_LOGGER, repository.save(ong15));
		
		Ong ong16 = new Ong
				(
						"ONG Zoé - Saúde para quem cuida da Floresta Amazônica", 
						1120129634, //55(11)3231-0800
						"01308000",
						"Casa", 
						"A ONG ZOÉ é uma associação sem fins lucrativos que apoia populações amazônicas com acesso limitado aos cuidados com a saúde.", 
						CATEGORIA_ONG_CID, 
						"contato@ongzoe.org", "$2a$10$ZoXuz.BbTEj8XIowlhDAYuxB.vidX0USLzowQFKNa4Yhv6b89uvZi", 
						"40.111.124/1102-00", 
						CNAE, 
						"112112", 
						"9901", 
						"Nubank",
						"contatoPix@zoe.com.br", 
						"141.999.182-71",
						ZONA_LESTE,
						LocalDate.parse("2023-05-17")
				);
		ong16.setEndereco("R. Barata Ribeiro, 414");
		logger.info(TEXTO_LOGGER, repository.save(ong16));
		
		Ong ong17 = new Ong
				(
						"Pela Vidda SP", 
						1120129634, //(11) 3259-2149
						"01223010",
						"Casa", 
						"O Grupo Pela Vidda/SP (Valorização, Integração e Dignidade do Doente de aids) é uma ONG sem fins lucrativos voltada a pessoas vivendo e convivendo com HIV/AIDS independente de gênero, orientação sexual, orientação político-partidária ou religiosa.", 
						"Saúde", 
						"gpvsp@uol.com.br", "$2a$10$nSl7gkrbZKtOebakaTiBAeRxqD1ZTeR3fW3oGG4u9J0zViQEPdMOi", 
						"50.111.124/1102-00", 
						CNAE, 
						"100023", 
						"9901", 
						"Banco Pam",
						"pelavidaPix@gmail.com", 
						"991.302.182-71",
						ZONA_LESTE,
						LocalDate.parse("2023-02-01")
				);
		ong17.setEndereco("Rua Gen. Jardim, 566");
		logger.info(TEXTO_LOGGER, repository.save(ong17));
		
		Ong ong18 = new Ong
				(
						"Grupo da Sopa / ONG Um Ato de Amor", 
						1120129634, //(11) 96332-4165
						"03110010",
						"Casa", 
						"O Grupo da Sopa foi criado em 1996, por iniciativa de um grupo de amigos que resolveu dedicar algumas horas semanais para ajudar o próximo. Durante os dois primeiros anos, esses amigos preparavam sopa para distribuir nas ruas de São Paulo.", 
						CATEGORIA_ONG_CID, 
						"contato@grupodasopa.org", "$2a$10$n4icYhzSWmj54r6YZtjbE.kwEmQjtXE2AjZIGWiDzbI.CGap5LxDO", 
						"60.111.124/1102-00", 
						CNAE, 
						"182523", 
						"9901", 
						"Banco Original",
						"grupodaSopa@hotmail.com", 
						"181.001.182-71",
						ZONA_LESTE,
						LocalDate.parse("2023-03-18")
				);
		ong18.setEndereco("R. Borges de Figueiredo, 86");
		logger.info(TEXTO_LOGGER, repository.save(ong18));
		
		Ong ong19 = new Ong
				(
						"ONG Desejando o Bem", 
						1120129634, //+55 11 2594-5268
						"04763060",
						"Casa", 
						"O desejo de ajudar crianças carentes surgiu em 2014, quando a idealizadora Simone e sua filha Bia conheceram a história de uma família que acabara de dar as boa-vindas a trigêmeas e que necessitava de enxoval não só para uma, mas para três meninas!", 
						CATEGORIA_ONG_CID, 
						"atendimento@desejandoobem.com.br", "$2a$10$.tJiLhDmK1fvBp3qMkNieu0iHbiBIS.8l7qh8W4ZrTT4NcM5Ww/5m", 
						"80.111.124/1102-00", 
						CNAE, 
						"112123", 
						"9902", 
						"Banco inter",
						"contatoPix@desejandoBem.com.br", 
						"011.001.162-71",
						ZONA_LESTE,
						LocalDate.parse("2023-05-22")
				);
		ong19.setEndereco("R. Amaro Leite, 88");
		logger.info(TEXTO_LOGGER, repository.save(ong19));
		
		Ong ong20 = new Ong
				(
						"ONG Brasil Eco Planetário", 
						1120129634, //(11) 99970-1819
						"04776100",
						"Casa", 
						"Trabalhar pela melhoria da relação entre o ser humano, sociedade e a natureza e a promoção da cultura da paz.", 
						"Meio Ambiente", 
						"contato@contato.com.br", "$2a$10$IEAvkUO9h30iDNB5nhczPujL.wpo4pMMneTosAecDXDxZDlmqhqSm", 
						"91.111.124/1102-00", 
						CNAE, 
						"134103", 
						"1900", 
						"Caixa",
						"brPlanetarioContato@gmail.com", 
						"851.001.182-01",
						ZONA_SUL,
						LocalDate.parse("2023-01-02")
				);
		ong20.setEndereco("Av. Engenheiro José Salles, 333");
		logger.info(TEXTO_LOGGER, repository.save(ong20));
		
		};
		
		
		
	}
	

	
	@Autowired
	MantemUserRepository userRepository;
		
	@Bean
	CommandLineRunner initDatabaseUser(MantemUserRepository repository, MantemUser repoCliente) {
		return args -> {
		repository.deleteAll();
		
		List<Long> user1Favoritos = new ArrayList<>(Arrays.asList((long) 1));
		User user1 = new User("Diogo", "Lima","DiogoLima50@gmail.com", "$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.", LocalDate.parse("2023-05-20"),user1Favoritos) ;
		logger.info(TEXTO_LOGGER, repository.save(user1));
		
		logger.info(TEXTO_LOGGER, repository.save(user2));
		
				
		};

	}
	
	@Autowired
	MantemAdminRepository adminRepository;
		
	@Bean
	CommandLineRunner initDatabaseAdmin(MantemAdminRepository repository, MantemAdmin repoCliente) {
		return args -> {
		repository.deleteAll();
				
		Admin admin1 = new Admin("Fernanda", "Sena de Souza","ScrumMaster4K@gmail.com", "$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.");
		logger.info(TEXTO_LOGGER, repository.save(admin1));
		
		};

	}
	
	@Autowired
	MantemExclusaoRepository excluiRepository;
		
	@Bean
	CommandLineRunner initDatabaseExclusao(MantemExclusaoRepository repository, MantemExclusao repoCliente) {
		return args -> {
		repository.deleteAll();
				
		Exclusao exclui = new Exclusao();
		logger.info(TEXTO_LOGGER, repository.save(exclui));
		
		};
	}
	
	
	
	
	@Autowired
	MantemComentarioRepository comentarioRepository;
		
	@Bean
	CommandLineRunner initDatabaseComentario(MantemComentarioRepository repository, MantemComentario repoCliente) {
		return args -> {
		repository.deleteAll();
		
		LocalDateTime localDateTime1 = LocalDateTime.of(2023, 06, 01, 20, 33, 48, 640000);
		
		Comentario comentario1 = new Comentario(ong1, user2, "A ONG é muito boa, o trabalho deles é lindo!", 4, localDateTime1);
		logger.info(TEXTO_LOGGER, repository.save(comentario1));
		
		};
	}
	

	

}
