package dev.ivank.trchatbotdemo.report.domain;

import dev.ivank.trchatbotdemo.common.Identifier;
import dev.ivank.trchatbotdemo.security.auth.domain.User;
import dev.ivank.trchatbotdemo.chat.domain.ChatInteraction;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SortNatural;

import java.time.Instant;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@NamedEntityGraph(
        name = Report.CHAT_INTERACTIONS_EAGER,
        attributeNodes = {
                @NamedAttributeNode(value = "chatInteractions", subgraph = "ci-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "ci-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("question"),
                                @NamedAttributeNode("questionLanguage"),
                                @NamedAttributeNode("answer"),
                                @NamedAttributeNode("askedAt"),
                                @NamedAttributeNode("timeToProcess"),
                        }
                )
        }
)
public class Report extends Identifier {
    public static final String CHAT_INTERACTIONS_EAGER = "chat-interactions-eager";

    @CreationTimestamp
    private Instant startedAt;
    private Instant finishedAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private ReportStatus reportStatus;
    @OneToMany(
            mappedBy = "report",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @SortNatural
    private final SortedSet<ChatInteraction> chatInteractions = new TreeSet<>();

    public Report() {
        this.reportStatus = ReportStatus.INITIALIZED;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public SortedSet<ChatInteraction> getChatInteractions() {
        return chatInteractions;
    }
}
