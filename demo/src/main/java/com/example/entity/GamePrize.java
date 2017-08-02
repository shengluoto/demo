package com.example.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Table(name = "gamePrize")
@Entity
public class GamePrize implements Serializable {

    private static final long serialVersionUID = -4573190333083207647L;

    @Id
    @GenericGenerator(name="uuid",strategy="uuid")
    @GeneratedValue(generator="uuid")
    @Column(name = "gamePrizeId", unique = true, nullable = false)
    private String gamePrizeId;
    // 奖品名
    private String prizeName;
    // 奖品中奖概率
    private Float prizeProbability;
    // 奖品数量
    private Integer prizeNumber;

    public String getGamePrizeId() {
        return gamePrizeId;
    }

    public void setGamePrizeId(String gamePrizeId) {
        this.gamePrizeId = gamePrizeId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Float getPrizeProbability() {
        return prizeProbability;
    }

    public void setPrizeProbability(Float prizeProbability) {
        this.prizeProbability = prizeProbability;
    }

    public Integer getPrizeNumber() {
        return prizeNumber;
    }

    public void setPrizeNumber(Integer prizeNumber) {
        this.prizeNumber = prizeNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gamePrizeId == null) ? 0 : gamePrizeId.hashCode());
        result = prime * result + ((prizeName == null) ? 0 : prizeName.hashCode());
        result = prime * result + ((prizeNumber == null) ? 0 : prizeNumber.hashCode());
        result = prime * result + ((prizeProbability == null) ? 0 : prizeProbability.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GamePrize other = (GamePrize) obj;
        if (gamePrizeId == null) {
            if (other.gamePrizeId != null)
                return false;
        } else if (!gamePrizeId.equals(other.gamePrizeId))
            return false;
        if (prizeName == null) {
            if (other.prizeName != null)
                return false;
        } else if (!prizeName.equals(other.prizeName))
            return false;
        if (prizeNumber == null) {
            if (other.prizeNumber != null)
                return false;
        } else if (!prizeNumber.equals(other.prizeNumber))
            return false;
        if (prizeProbability == null) {
            if (other.prizeProbability != null)
                return false;
        } else if (!prizeProbability.equals(other.prizeProbability))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "GamePrize [gamePrizeId=" + gamePrizeId + ", prizeName=" + prizeName
                + ", prizeProbability=" + prizeProbability + ", prizeNumber=" + prizeNumber + "]";
    }

}
