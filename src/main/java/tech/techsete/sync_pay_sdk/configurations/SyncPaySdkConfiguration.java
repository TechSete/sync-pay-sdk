package tech.techsete.sync_pay_sdk.configurations;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Configuração principal do SDK SyncPay.
 * <p>
 * Esta classe é responsável pela configuração automática do SDK SyncPay,
 * habilitando a funcionalidade de auto-configuração do Spring Boot e definindo
 * o escaneamento de componentes para o pacote base do SDK.
 * </p>
 * <p>
 * A anotação {@link AutoConfiguration} permite que o Spring Boot configure automaticamente
 * este SDK quando ele é incluído como dependência em um projeto, simplificando a integração.
 * </p>
 * <p>
 * A anotação {@link ComponentScan} garante que todos os componentes, serviços e configurações
 * dentro do pacote base do SDK (tech.techsete.sync_pay_sdk) sejam detectados e registrados
 * como beans no contexto da aplicação Spring.
 * </p>
 * <p>
 * Este SDK facilita a integração com os serviços da SyncPay, incluindo processamento
 * de cobranças via PIX e gerenciamento de transações.
 * </p>
 *
 * @see tech.techsete.sync_pay_sdk.services.AuthenticationService
 * @see tech.techsete.sync_pay_sdk.services.PixService
 * @see tech.techsete.sync_pay_sdk.services.WebhookService
 *
 * @author Edson Isaac
 */
@AutoConfiguration
@ComponentScan("tech.techsete.sync_pay_sdk")
public class SyncPaySdkConfiguration { }