package com.vr.miniautorizador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vr.miniautorizador.dto.CartaoDTO;
import com.vr.miniautorizador.dto.TransacaoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MiniAutorizadorApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private String CARTOES_PATH = "/cartoes";
    private String TRANSACOES_PATH = "/transacoes";
    private String senha_cartao = "1234";
    private Double valor_inicial_cartao = 500D;

    @Test
    void contextLoads() {
    }

    @Test
    public void criaEValidaCartaoAposEfetivarTransacao() throws Exception {

        String numeroCartao = String.valueOf(geraNumeroCartao());

        criarCartaoTest(numeroCartao);

        Double valorTransacao = criarTransacaoTeste(numeroCartao);

        String saldoResultante = String.valueOf(this.valor_inicial_cartao - valorTransacao);

        this.mockMvc.perform(get(CARTOES_PATH + "/" + numeroCartao))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(saldoResultante)));
    }

    private void criarCartaoTest(String numeroCartao) throws Exception {
        this.mockMvc.perform(post(CARTOES_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gerarCartaoDTOJson(numeroCartao)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString(numeroCartao)));
    }

    private Double criarTransacaoTeste(String numeroCartao) throws Exception {
        Double valorTransacao = geraValorTransacao();
        this.mockMvc.perform(post(TRANSACOES_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(geraTransacaoDTOJson(numeroCartao, valorTransacao)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString(numeroCartao)));
        return valorTransacao;
    }

    private String gerarCartaoDTOJson(String numeroCartao) throws JsonProcessingException {
        CartaoDTO cartaoDTO = new CartaoDTO(numeroCartao, senha_cartao);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(cartaoDTO);
    }

    private Integer geraNumeroCartao() {
        Random rand = new Random();
        int upperbound = 999999999;
        return rand.nextInt(upperbound);
    }

    private Double geraValorTransacao() {
        Random rand = new Random();
        int upperbound = 100;
        return Double.parseDouble("" + rand.nextInt(upperbound));
    }

    private String geraTransacaoDTOJson(
            String numeroCartao, Double valorTransacao) throws JsonProcessingException {
        TransacaoDTO transacaoDTO = new TransacaoDTO(numeroCartao, senha_cartao, valorTransacao);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(transacaoDTO);
    }

}
