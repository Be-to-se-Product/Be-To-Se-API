package com.be.two.c.apibetwoc.service.imagem;


import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.ImagemRepository;
import com.be.two.c.apibetwoc.service.arquivo.ArquivoService;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.service.imagem.mapper.ImagemMapper;
import com.be.two.c.apibetwoc.util.PilhaObj;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
@RequiredArgsConstructor
public class ImagemService {

    private final ArquivoService arquivoService;
    private final HttpServletRequest request;
    public Imagem cadastrarImagensProduto(MultipartFile file, TipoArquivo tipoArquivo, Produto produto, PilhaObj<ArquivoSaveDTO> arquivos){
        ArquivoSaveDTO arquivo= arquivoService.salvarArquivo(file,tipoArquivo,arquivos);
        return ImagemMapper.of(arquivo,produto);
    }


    public Imagem formatterImagensURI(Imagem imagem){
        String dominio = request.getRequestURL().toString().replace(request.getRequestURI(), "/imagens/");
        imagem.setNomeReferencia(dominio +""+ imagem.getNomeReferencia());
        return imagem;
    }
}
