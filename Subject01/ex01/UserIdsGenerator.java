/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   UserIdsGenerator.java                              :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/06 19:03:45 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/24 13:26:41 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private int lastGeneratedId;

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public int generateId() {
        lastGeneratedId++;
        return lastGeneratedId;
    }
}
