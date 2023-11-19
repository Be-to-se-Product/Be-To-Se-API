package com.be.two.c.apibetwoc.service.imagem;


import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.ImagemRepository;
import com.be.two.c.apibetwoc.service.arquivo.ArquivoService;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.service.imagem.mapper.ImagemMapper;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagemService {

    private final ImagemRepository imagemRepository;
    private final ArquivoService arquivoService;
    private final HttpServletRequest request;
    public Imagem cadastrarImagensProduto(MultipartFile file, TipoArquivo tipoArquivo, Produto produto){
        ArquivoSaveDTO arquivo= arquivoService.salvarArquivo(file,tipoArquivo);
        return imagemRepository.save(ImagemMapper.of(arquivo,produto));
    }


    public List<Imagem> formatterImagensURI(List<Imagem> imagens){
        String dominio = request.getRequestURL().toString().replace(request.getRequestURI(), "/");
        imagens.forEach(element-> element.setNomeReferencia(dominio +""+ element.getNomeReferencia()));
        return imagens;
    }
}
