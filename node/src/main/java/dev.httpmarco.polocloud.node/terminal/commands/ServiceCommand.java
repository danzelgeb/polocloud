package dev.httpmarco.polocloud.node.terminal.commands;

import dev.httpmarco.polocloud.api.services.ClusterService;
import dev.httpmarco.polocloud.node.Node;
import dev.httpmarco.polocloud.node.commands.Command;
import dev.httpmarco.polocloud.node.commands.CommandArgumentType;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class ServiceCommand extends Command {

    public ServiceCommand() {
        super("service", "Manage all your services", "services", "ser");

        var serviceArgument = CommandArgumentType.ClusterService("service");

        syntax(it -> {
            var serviceProvider = Node.instance().serviceProvider();

            log.info("Following &b{} &7groups are loading&8:", serviceProvider.services().size());
            serviceProvider.services().forEach(group -> log.info("&8- &f{}&8: (&7{}&8)", group.name(), group.details()));
        }, CommandArgumentType.Keyword("list"));

        syntax(it -> {
            var service = it.arg(serviceArgument);
            log.info("Name&8: &b{}", service.name());
            log.info("Running node&8: &b{}", String.join("&8, &b", service.runningNode()));
            log.info("Group&8: &b{}", service.group().name());
            log.info("Bind&8: &b{}&8:&b{}", service.hostname(), service.port());
            log.info("State&8: &b{}", service.state());
        }, serviceArgument, CommandArgumentType.Keyword("info"));


        syntax(it -> {
            var service = it.arg(serviceArgument);
            log.info("Try shutdown of &8'&f{}'&8", service.name());

            synchronized (this) {
                service.shutdown();
            }
        }, serviceArgument, CommandArgumentType.Keyword("shutdown"));
    }
}
