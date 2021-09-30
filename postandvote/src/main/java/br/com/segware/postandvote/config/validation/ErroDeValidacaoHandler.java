package br.com.segware.postandvote.config.validation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.segware.postandvote.config.ErroDeFormularioException;
import br.com.segware.postandvote.config.dto.ErroDeFormularioDTO;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDTO> capturarValidacaoDeFormulario(MethodArgumentNotValidException exception) {
		
		List<FieldError> fieldErrors =  exception.getBindingResult().getFieldErrors();
		
		List<ErroDeFormularioDTO> dto = fieldErrors.stream()
			.map((erro) -> {
			
				String mensagem = this.messageSource.getMessage(erro, LocaleContextHolder.getLocale());
			
				return new ErroDeFormularioDTO(erro.getField(), mensagem);
			
				})
			.collect(Collectors.toList());
		
		return dto;
		
	}
	
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ErroDeFormularioException.class)
	public ErroDeFormularioDTO capturarValidacaoDeItem(ErroDeFormularioException exception) {

		return new ErroDeFormularioDTO(exception);
		
	}
	
	
	
}
