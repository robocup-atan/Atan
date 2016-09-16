package com.github.robocup_atan.atan.model;

//~--- non-JDK imports --------------------------------------------------------

import com.github.robocup_atan.atan.model.enums.PlayMode;

import com.github.robocup_atan.atan.parser.CommandFilter;
import com.github.robocup_atan.atan.parser.Filter;
import com.github.robocup_atan.atan.parser.trainer.CmdParserTrainer;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.StringReader;

/**
 * A simple implementation of AbstractUDPClient for Trainers.
 *
 * @author Atan
 */
public class SServerTrainer extends AbstractUDPClient implements ActionsTrainer {
    private static final int           TRAINER_PORT   = 6001;
    private static Logger              log            = Logger.getLogger(SServerTrainer.class);
    private String                     initMessage    = null;
    private final CmdParserTrainer     parser         = new CmdParserTrainer(new StringReader(""));
    private final Filter               filter         = new Filter();
    private final CommandFactory       commandFactory = new CommandFactory();
    private final SServerCommandBuffer cmdBuf         = new SServerCommandBuffer();
    private ControllerTrainer          controller;
    private String                     teamName;

    /**
     * A part constructor for SServerTrainer (assumes localhost:6001)
     *
     * @param teamName The team name.
     * @param t A ControllerTrainer.
     */
    public SServerTrainer(String teamName, ControllerTrainer t) {
        this(teamName, t, TRAINER_PORT, "localhost");
    }

    /**
     * The full constructor for SServerTrainer
     *
     * @param teamName The teams name.
     * @param t A ControllerTrainer.
     * @param port The port to connect to.
     * @param hostname The host address.
     */
    public SServerTrainer(String teamName, ControllerTrainer t, int port, String hostname) {
        super(port, hostname);
        this.teamName   = teamName;
        this.controller = t;
    }

    /** {@inheritDoc} */
    @Override
    public String getInitMessage() {
        return initMessage;
    }

    /**
     * Connects to the server via AbstractUDPClient.
     */
    public void connect() {
        CommandFactory f = new CommandFactory();
        f.addTrainerInitCommand();
        initMessage = f.next();
        super.start();
        super.setName("Trainer");
    }

    /** {@inheritDoc} */
    @Override
    public void received(String msg) throws IOException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("<---'" + msg + "'");
            }
            filter.run(msg, cmdBuf);
            cmdBuf.takeStep(controller, parser, this);
            while (commandFactory.hasNext()) {
                String cmd = commandFactory.next();
                if (log.isDebugEnabled()) {
                    log.debug("--->'" + cmd + "'");
                }
                send(cmd);
                pause(50);
            }
        } catch (Exception ex) {
            log.error("Error while receiving message: " + msg + " " + ex.getMessage(), ex);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void changePlayMode(PlayMode playMode) {
        this.commandFactory.addChangePlayModeCommand(playMode);
    }

    /** {@inheritDoc} */
    @Override
    public void movePlayer(ActionsPlayer p, double x, double y) {
        this.commandFactory.addMovePlayerCommand(p, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public void moveBall(double x, double y) {
        this.commandFactory.addMoveBallCommand(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public void checkBall() {
        this.commandFactory.addCheckBallCommand();
    }

    /** {@inheritDoc} */
    @Override
    public void startGame() {
        this.commandFactory.addStartCommand();
    }

    /** {@inheritDoc} */
    @Override
    public void recover() {
        this.commandFactory.addRecoverCommand();
    }

    /** {@inheritDoc} */
    @Override
    public void eye(boolean eyeOn) {
        this.commandFactory.addEyeCommand(eyeOn);
    }

    /** {@inheritDoc} */
    @Override
    public void ear(boolean earOn) {
        this.commandFactory.addEarCommand(earOn);
    }

    /** {@inheritDoc} */
    @Override
    public void look() {
        this.commandFactory.addLookCommand();
    }

    /** {@inheritDoc} */
    @Override
    public void teamNames() {
        this.commandFactory.addTeamNamesCommand();
    }

    /** {@inheritDoc} */
    @Override
    public void changePlayerType(String teamName, int unum, int playerType) {
        this.commandFactory.addChangePlayerTypeCommand(teamName, unum, playerType);
    }

    /** {@inheritDoc} */
    @Override
    public void say(String message) {
        this.commandFactory.addSayCommand(message);
    }

    /** {@inheritDoc} */
    @Override
    public void bye() {
        this.commandFactory.addByeCommand();
    }

    /** {@inheritDoc} */
    @Override
    public void handleError(String error) {
        log.error(error);
    }

    /**
     * Pause the thread.
     * @param ms How long to pause the thread for (in ms).
     */
    private synchronized void pause(int ms) {
        try {
            this.wait(ms);
        } catch (InterruptedException ex) {
            log.warn("Interrupted Exception ", ex);
        }
    }

    /**
     * A private controller-style filter
     * @author Atan
     */
    private static class SServerCommandBuffer implements CommandFilter {
        private String changePlayerTypeCommand = null;
        private String errorCommand            = null;
        private String hearCommand             = null;
        private String initCommand             = null;
        private String okCommand               = null;
        private String playerParamCommand      = null;
        private String playerTypeCommand       = null;
        private String seeCommand              = null;
        private String senseBodyCommand        = null;
        private String serverParamCommand      = null;
        private String warningCommand          = null;

        /**
         * @inheritDoc
         */
        @Override
        public void seeCommand(String cmd) {
            seeCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void hearCommand(String cmd) {
            hearCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void senseBodyCommand(String cmd) {
            senseBodyCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void initCommand(String cmd) {
            initCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void errorCommand(String cmd) {
            errorCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void serverParamCommand(String cmd) {
            serverParamCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void playerParamCommand(String cmd) {
            playerParamCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void playerTypeCommand(String cmd) {
            playerTypeCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void changePlayerTypeCommand(String cmd) {
            changePlayerTypeCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void okCommand(String cmd) {
            okCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void warningCommand(String cmd) {
            warningCommand = cmd;
        }

        /**
         * Decide the step to take.
         * @param controller
         * @param parser
         * @param c
         * @throws Exception
         */
        public void takeStep(ControllerTrainer controller, CmdParserTrainer parser, ActionsTrainer c) throws Exception {
            if (seeCommand != null) {
                parser.parseSeeCommand(seeCommand, controller, c);
                seeCommand = null;
            }
            if (hearCommand != null) {
                parser.parseHearCommand(hearCommand, controller, c);
                hearCommand = null;
            }
            if (senseBodyCommand != null) {
                parser.parseSenseBodyCommand(senseBodyCommand, controller, c);
                senseBodyCommand = null;
            }
            if (initCommand != null) {
                parser.parseInitCommand(initCommand, controller, c);
                initCommand = null;
            }
            if (okCommand != null) {
                parser.parseOkCommand(okCommand, controller, c);
                okCommand = null;
            }
            if (warningCommand != null) {
                parser.parseWarningCommand(warningCommand, controller, c);
                warningCommand = null;
            }
            if (serverParamCommand != null) {
                parser.parseServerParamCommand(serverParamCommand, controller, c);
                serverParamCommand = null;
            }
            if (playerParamCommand != null) {
                parser.parsePlayerParamCommand(playerParamCommand, controller, c);
                playerParamCommand = null;
            }
            if (playerTypeCommand != null) {
                parser.parsePlayerTypeCommand(playerTypeCommand, controller, c);
                playerTypeCommand = null;
            }
            if (changePlayerTypeCommand != null) {
                parser.parseChangePlayerTypeCommand(changePlayerTypeCommand, controller, c);
                changePlayerTypeCommand = null;
            }
            if (errorCommand != null) {
                parser.parseErrorCommand(errorCommand, controller, c);
                errorCommand = null;
            }
        }
    }
}
